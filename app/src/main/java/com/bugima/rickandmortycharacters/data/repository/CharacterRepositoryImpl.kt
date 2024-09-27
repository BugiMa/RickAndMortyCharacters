package com.bugima.rickandmortycharacters.data.repository

import com.bugima.rickandmortycharacters.data.local.dao.FavoriteCharacterDao
import com.bugima.rickandmortycharacters.data.remote.api.RickAndMortyApi
import com.bugima.rickandmortycharacters.domain.mapper.toData
import com.bugima.rickandmortycharacters.domain.mapper.toDomain
import com.bugima.rickandmortycharacters.domain.model.Character
import com.bugima.rickandmortycharacters.domain.repository.CharacterRepository
import com.bugima.rickandmortycharacters.util.ErrorMessage
import com.bugima.rickandmortycharacters.util.Resource
import retrofit2.HttpException
import java.io.IOException

class CharacterRepositoryImpl(
    private val apiService: RickAndMortyApi,
    private val favoriteCharacterDao: FavoriteCharacterDao,
): CharacterRepository {

    private var cachedPageCount: Int? = null

    override suspend fun fetchCharacters(page: Int): Resource<Set<Character>> {
        return try {
            apiService.getCharacters(page).let { response ->
                response.takeIf { it.isSuccessful }?.body()?.let { responseBody ->
                    cachedPageCount = responseBody.info.pageCount
                    Resource.Success(responseBody.characters.map { it.toDomain() }.toSet())
                } ?: run {
                    Resource.Error(response.errorBody()?.string() ?: ErrorMessage.UNKNOWN)
                }
            }
        } catch (e: IOException) {
            Resource.Error(ErrorMessage.NO_INTERNET, e)
        } catch (e: HttpException) {
            Resource.Error("${ErrorMessage.SERVER} ${e.code()} ${e.message()}", e)
        } catch (e: Exception) {
            Resource.Error(ErrorMessage.UNKNOWN, e)
        }
    }

    override fun getCharacterPageCount(): Int? = cachedPageCount

    override suspend fun addCharacterToFavorites(character: Character) {
        favoriteCharacterDao.addToFavorites(character.toData())
    }

    override suspend fun removeCharacterFromFavoritesById(character: Character) {
        favoriteCharacterDao.removeFromFavorites(character.toData())
    }

    override suspend fun getAllFavoritesIds(): Set<Int> = favoriteCharacterDao.getAllFavoritesIds().toSet()
}
