package com.bugima.rickandmortycharacters.domain.usecase

import com.bugima.rickandmortycharacters.domain.repository.CharacterRepository
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import org.junit.After
import org.junit.Before
import org.junit.Test

class GetCharacterPageCountUseCaseTest {
    private lateinit var characterRepository: CharacterRepository
    private lateinit var getCharacterPageCountUseCase: GetCharacterPageCountUseCase

    @Before
    fun setup() {
        characterRepository = mockk()
        getCharacterPageCountUseCase = GetCharacterPageCountUseCase(characterRepository)
    }

    @After
    fun teardown() {
        clearAllMocks()
    }
    @Test
    fun `invoke should return character page count from repository`() {
        // Given
        val expectedPageCount = 5
        coEvery { characterRepository.getCharacterPageCount() } returns expectedPageCount

        // When
        val result = getCharacterPageCountUseCase()

        // Then
        coVerify { characterRepository.getCharacterPageCount() }
        assert(result == expectedPageCount)
    }
}
