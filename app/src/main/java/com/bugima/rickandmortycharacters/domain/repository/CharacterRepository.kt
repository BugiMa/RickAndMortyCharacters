package com.bugima.rickandmortycharacters.domain.repository

import com.bugima.rickandmortycharacters.domain.model.Character

interface CharacterRepository {
    suspend fun fetchCharacters(page: Int): Set<Character>
    fun getCharacterPageCount(): Int?
    suspend fun addCharacterToFavorites(character: Character)
    suspend fun removeCharacterFromFavoritesById(character: Character)
    suspend fun getAllFavoritesIds(): Set<Int>
}