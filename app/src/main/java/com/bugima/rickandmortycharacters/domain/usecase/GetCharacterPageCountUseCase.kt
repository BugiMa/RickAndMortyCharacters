package com.bugima.rickandmortycharacters.domain.usecase

import com.bugima.rickandmortycharacters.domain.repository.CharacterRepository
import javax.inject.Inject

class GetCharacterPageCountUseCase
@Inject constructor(
    private val characterRepository: CharacterRepository
) {
    operator fun invoke(): Int? {
        return characterRepository.getCharacterPageCount()
    }
}
