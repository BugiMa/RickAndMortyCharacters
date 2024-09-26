package com.bugima.rickandmortycharacters.data.repository

import com.bugima.rickandmortycharacters.data.local.dao.FavoriteCharacterDao
import com.bugima.rickandmortycharacters.data.remote.api.RickAndMortyApi
import com.bugima.rickandmortycharacters.domain.mapper.toData
import com.bugima.rickandmortycharacters.domain.mapper.toDomain
import com.bugima.rickandmortycharacters.domain.model.Character
import com.bugima.rickandmortycharacters.domain.repository.CharacterRepository

class CharacterRepositoryImpl(
    private val apiService: RickAndMortyApi,
    private val favoriteCharacterDao: FavoriteCharacterDao,
): CharacterRepository {

    private var cachedPageCount: Int? = null

    override suspend fun fetchCharacters(page: Int): Set<Character> = apiService.getCharacters(page)
        .also { cachedPageCount = it.info.pageCount }
        .characters
        .map { it.toDomain() }
        .toSet()

    override fun getCharacterPageCount(): Int? = cachedPageCount

    override suspend fun addCharacterToFavorites(character: Character) {
        favoriteCharacterDao.addToFavorites(character.toData())
    }

    override suspend fun removeCharacterFromFavoritesById(character: Character) {
        favoriteCharacterDao.removeFromFavorites(character.toData())
    }

    override suspend fun getAllFavoritesIds(): Set<Int> = favoriteCharacterDao.getAllFavoritesIds().toSet()
}