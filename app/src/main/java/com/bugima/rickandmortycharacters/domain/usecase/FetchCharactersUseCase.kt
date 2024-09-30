package com.bugima.rickandmortycharacters.domain.usecase

import com.bugima.rickandmortycharacters.domain.model.Character
import com.bugima.rickandmortycharacters.domain.repository.CharacterRepository
import javax.inject.Inject

class FetchCharactersUseCase
@Inject constructor(
    private val characterRepository: CharacterRepository,
) {
    suspend operator fun invoke(page: Int): Set<Character> =
        characterRepository.fetchCharacters(page)
}
