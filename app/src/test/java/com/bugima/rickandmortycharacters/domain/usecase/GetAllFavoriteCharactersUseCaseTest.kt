package com.bugima.rickandmortycharacters.domain.usecase

import com.bugima.rickandmortycharacters.domain.model.Character
import com.bugima.rickandmortycharacters.domain.repository.CharacterRepository
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test

class GetAllFavoriteCharactersUseCaseTest {
    private lateinit var characterRepository: CharacterRepository
    private lateinit var getAllFavoriteCharactersUseCase: GetAllFavoriteCharactersUseCase

    @Before
    fun setup() {
        characterRepository = mockk()
        getAllFavoriteCharactersUseCase = GetAllFavoriteCharactersUseCase(characterRepository)
    }

    @After
    fun teardown() {
        clearAllMocks()
    }
    @Test
    fun `invoke should get all favorite characters from repository`() = runTest {
        // Given
        val expectedFavorites = setOf(Character(id = 1, name = "Rick", imageUrl = ""))
        coEvery { characterRepository.getAllFavorites() } returns expectedFavorites

        // When
        val result = getAllFavoriteCharactersUseCase()

        // Then
        coVerify { characterRepository.getAllFavorites() }
        assert(result == expectedFavorites)

    }
}
