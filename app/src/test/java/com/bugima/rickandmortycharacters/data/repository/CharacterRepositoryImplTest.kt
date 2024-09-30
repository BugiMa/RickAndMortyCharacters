package com.bugima.rickandmortycharacters.data.repository

import com.bugima.rickandmortycharacters.data.local.dao.FavoriteCharacterDao
import com.bugima.rickandmortycharacters.data.remote.api.RickAndMortyApi
import com.bugima.rickandmortycharacters.data.remote.dto.CharacterDto
import com.bugima.rickandmortycharacters.data.remote.dto.PagedCharactersResponseDto
import com.bugima.rickandmortycharacters.data.remote.dto.ResponseInfoDto
import com.bugima.rickandmortycharacters.domain.mapper.toData
import com.bugima.rickandmortycharacters.domain.mapper.toDomain
import com.bugima.rickandmortycharacters.domain.model.Character
import io.mockk.clearAllMocks
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockkClass
import io.mockk.mockkStatic
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertThrows
import retrofit2.HttpException
import retrofit2.Response

class CharacterRepositoryImplTest {
    private lateinit var apiService: RickAndMortyApi
    private lateinit var favoriteCharacterDao: FavoriteCharacterDao
    private lateinit var characterRepository: CharacterRepositoryImpl

    @Before
    fun setup() {
        apiService = mockk()
        favoriteCharacterDao = mockk(relaxed = true)
        characterRepository = CharacterRepositoryImpl(apiService, favoriteCharacterDao)
    }

    @After
    fun teardown() {
        clearAllMocks()
    }

    @Test
    fun `fetchCharacters should return characters when response is successful`() = runTest {
        // Given:
        val page = 1
        val responseBody = PagedCharactersResponseDto(
            info = ResponseInfoDto(pageCount = 1),
            characters = listOf(
                CharacterDto(id = 1, name = "Rick", imageUrl = ""),
                CharacterDto(id = 2, name = "Morty", imageUrl = "")
            )
        )
        val response = Response.success(responseBody)
        coEvery { apiService.getCharacters(page) } returns response

        // When:
        val result = characterRepository.fetchCharacters(page)

        // Then:
        coVerify { apiService.getCharacters(page) }
        assert(result == responseBody.characters.map { it.toDomain() }.toSet())
    }

    @Test(expected = HttpException::class)
    fun `fetchCharacters should throw HttpException when response is not successful`(): Unit = runTest {
        // Given:
        val page = 1
        var exceptionThrown = false
        val errorResponse = mockk<Response<PagedCharactersResponseDto>>(relaxed = true)
        every { errorResponse.isSuccessful } returns false
        every { errorResponse.code() } returns 500
        coEvery { apiService.getCharacters(page) } returns errorResponse

        // When:
            characterRepository.fetchCharacters(page)
    }

    @Test
    fun `addCharacterToFavorites should call Dao method`() = runTest {
        // Given:
        val character = Character(1, "Rick", "")

        // When:
        characterRepository.addCharacterToFavorites(character)

        //Then:
        coVerify { favoriteCharacterDao.addToFavorites(character.toData()) }
    }

    @Test
    fun `removeCharacterFromFavoritesById should call Dao method`() = runTest {
        // Given:
        val character = Character(1, "Rick", "")

        // When:
        characterRepository.removeCharacterFromFavorites(character)

        // Then:
        coVerify { favoriteCharacterDao.removeFromFavorites(character.toData()) }
    }

    @Test
    fun `getAllFavorites should return all favorite characters`() = runTest {
        // Given:
        val favoriteCharacters = setOf(
            Character(id = 1, name = "Rick", imageUrl = ""),
            Character(id = 2, name = "Morty", imageUrl = "")
        )
        coEvery { favoriteCharacterDao.getAllFavorites() } returns favoriteCharacters.map { it.toData() }

        // When:
        val result = characterRepository.getAllFavorites()

        // Then:
        coVerify { favoriteCharacterDao.getAllFavorites() }
        assert(result == favoriteCharacters)
    }

    @Test
    fun `getCharacterPageCount should return cached page count`() = runTest {
        // Given:
        assert(characterRepository.getCharacterPageCount() == null)
        val page = 1
        val pageCount = 5
        val responseBody = PagedCharactersResponseDto(
            info = ResponseInfoDto(pageCount = pageCount),
            characters = listOf(
                CharacterDto(id = 1, name = "Rick", imageUrl = ""),
                CharacterDto(id = 2, name = "Morty", imageUrl = "")
            )
        )
        val response = Response.success(responseBody)
        coEvery { apiService.getCharacters(page) } returns response

        // When:
        characterRepository.fetchCharacters(page)

        // Then:
        coVerify { apiService.getCharacters(page) }
        assert(characterRepository.getCharacterPageCount() == pageCount)
    }

}