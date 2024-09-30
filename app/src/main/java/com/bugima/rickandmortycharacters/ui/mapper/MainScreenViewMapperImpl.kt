package com.bugima.rickandmortycharacters.ui.mapper

import com.bugima.rickandmortycharacters.domain.model.Character
import com.bugima.rickandmortycharacters.ui.CharacterViewState

class MainScreenViewMapperImpl: MainScreenViewMapper {

    override fun mapToCharacterViewStateList(
        allCharacters: Set<Character>,
        favoriteCharactersIds: List<Int>
    ): List<CharacterViewState> = allCharacters.map { character ->
        character.toViewState(favoriteCharactersIds.any { it == character.id })
    }

    override fun mapToCharacterViewStateList(
        favoriteCharacters: Set<Character>
    ): List<CharacterViewState> = favoriteCharacters.map { it.toViewState(true) }

    private fun Character.toViewState(isFavorite: Boolean) = CharacterViewState(
        id = id,
        name = name,
        imageUrl = imageUrl,
        isFavorite = isFavorite
    )
}
