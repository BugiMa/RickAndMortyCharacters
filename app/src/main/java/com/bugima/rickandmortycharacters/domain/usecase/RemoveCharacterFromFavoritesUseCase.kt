package com.bugima.rickandmortycharacters.domain.usecase

import com.bugima.rickandmortycharacters.domain.model.Character
import com.bugima.rickandmortycharacters.domain.repository.CharacterRepository
import javax.inject.Inject

class RemoveCharacterFromFavoritesUseCase @Inject constructor(
    private val characterRepository: CharacterRepository
) {
    suspend operator fun invoke(character: Character) {
        return characterRepository.removeCharacterFromFavorites(character)
    }
}