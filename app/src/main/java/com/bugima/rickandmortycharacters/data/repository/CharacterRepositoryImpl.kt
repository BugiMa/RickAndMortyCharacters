package com.bugima.rickandmortycharacters.data.repository

import com.bugima.rickandmortycharacters.data.local.dao.FavoriteCharacterDao
import com.bugima.rickandmortycharacters.data.remote.api.RickAndMortyApi
import com.bugima.rickandmortycharacters.domain.mapper.toData
import com.bugima.rickandmortycharacters.domain.mapper.toDomain
import com.bugima.rickandmortycharacters.domain.model.Character
import com.bugima.rickandmortycharacters.domain.repository.CharacterRepository
import retrofit2.HttpException
import javax.inject.Inject

class CharacterRepositoryImpl
@Inject constructor(
    private val apiService: RickAndMortyApi,
    private val favoriteCharacterDao: FavoriteCharacterDao,
) : CharacterRepository {
    private var cachedPageCount: Int? = null

    override suspend fun fetchCharacters(page: Int): Set<Character> =
        apiService.getCharacters(page).let { response ->
            response.takeIf { it.isSuccessful }?.body()?.let { responseBody ->
                cachedPageCount = responseBody.info.pageCount
                responseBody.characters.map { it.toDomain() }.toSet()
            } ?: run {
                throw HttpException(response)
            }
        }

    override fun getCharacterPageCount(): Int? = cachedPageCount

    override suspend fun addCharacterToFavorites(character: Character) {
        favoriteCharacterDao.addToFavorites(character.toData())
    }

    override suspend fun removeCharacterFromFavorites(character: Character) {
        favoriteCharacterDao.removeFromFavorites(character.toData())
    }

    override suspend fun getAllFavorites(): Set<Character> =
        favoriteCharacterDao.getAllFavorites().map { it.toDomain() }.toSet()
}
