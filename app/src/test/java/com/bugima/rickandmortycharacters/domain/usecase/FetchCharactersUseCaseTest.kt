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

class FetchCharactersUseCaseTest {
    private lateinit var characterRepository: CharacterRepository
    private lateinit var fetchCharactersUseCase: FetchCharactersUseCase

    @Before
    fun setup() {
        characterRepository = mockk()
        fetchCharactersUseCase = FetchCharactersUseCase(characterRepository)
    }

    @After
    fun teardown() {
        clearAllMocks()
    }

    @Test
    fun `invoke should fetch characters from repository`() = runTest {
        // Given
        val page = 1
        val expectedCharacters = setOf(Character(id = 1, name = "Rick", imageUrl = ""))
        coEvery { characterRepository.fetchCharacters(page) } returns expectedCharacters

        // When
        val result = fetchCharactersUseCase(page)

        // Then
        coVerify { characterRepository.fetchCharacters(page) }
        assert(result == expectedCharacters)
    }
}
