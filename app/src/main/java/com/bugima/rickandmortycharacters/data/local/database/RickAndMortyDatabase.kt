package com.bugima.rickandmortycharacters.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bugima.rickandmortycharacters.data.local.dao.FavoriteCharacterDao
import com.bugima.rickandmortycharacters.data.local.entity.CharacterEntity

@Database(entities = [CharacterEntity::class], version = 1)
abstract class RickAndMortyDatabase: RoomDatabase() {
    abstract fun favoriteCharacterDao(): FavoriteCharacterDao
}
