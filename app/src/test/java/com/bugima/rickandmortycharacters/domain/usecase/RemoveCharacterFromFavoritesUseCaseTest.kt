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

class RemoveCharacterFromFavoritesUseCaseTest {
    private lateinit var characterRepository: CharacterRepository
    private lateinit var removeCharacterFromFavoritesUseCase: RemoveCharacterFromFavoritesUseCase

    @Before
    fun setup() {
        characterRepository = mockk()
        removeCharacterFromFavoritesUseCase = RemoveCharacterFromFavoritesUseCase(characterRepository)
    }

    @After
    fun teardown() {
        clearAllMocks()
    }
    @Test
    fun `invoke should remove character from favorites by ID`() = runTest {
        // Given
        val character = Character(id = 1, name = "Rick", imageUrl = "")
        coEvery { characterRepository.removeCharacterFromFavorites(character) } returns Unit
        // When
        removeCharacterFromFavoritesUseCase(character)

        // Then
        coVerify { characterRepository.removeCharacterFromFavorites(character) }
    }
}
