package com.bugima.rickandmortycharacters.data.local.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bugima.rickandmortycharacters.data.local.entity.CharacterEntity

interface FavoriteCharacterDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToFavorites(character: CharacterEntity)

    @Delete
    suspend fun removeFromFavorites(character: CharacterEntity)

    @Query("SELECT id FROM favorite_characters")
    suspend fun getAllFavoritesIds(): List<Int>
}
