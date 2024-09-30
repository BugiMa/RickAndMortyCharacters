package com.bugima.rickandmortycharacters.ui.mapper

import com.bugima.rickandmortycharacters.domain.model.Character
import com.bugima.rickandmortycharacters.ui.CharacterViewState

interface MainScreenViewMapper {
    fun mapToCharacterViewStateList(
        allCharacters: Set<Character>,
        favoriteCharactersIds: List<Int>
    ): List<CharacterViewState>

    fun mapToCharacterViewStateList(
        favoriteCharacters: Set<Character>
    ): List<CharacterViewState>
}
