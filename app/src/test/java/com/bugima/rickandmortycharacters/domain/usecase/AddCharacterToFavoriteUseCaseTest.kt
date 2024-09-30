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

class AddCharacterToFavoriteUseCaseTest {
    private lateinit var characterRepository: CharacterRepository
    private lateinit var addCharacterToFavoritesUseCase: AddCharacterToFavoritesUseCase

    @Before
    fun setup() {
        characterRepository = mockk()
        addCharacterToFavoritesUseCase = AddCharacterToFavoritesUseCase(characterRepository)
    }

    @After
    fun teardown() {
        clearAllMocks()
    }

    @Test
    fun `invoke should add character to favorites`() = runTest {
        // Given
        val character = Character(id = 1, name = "Rick", imageUrl = "")
        coEvery { characterRepository.addCharacterToFavorites(character) } returns Unit

        // When
        addCharacterToFavoritesUseCase(character)

        // Then
        coVerify { characterRepository.addCharacterToFavorites(character) }
    }
}
