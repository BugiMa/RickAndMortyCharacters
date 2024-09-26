package com.bugima.rickandmortycharacters.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bugima.rickandmortycharacters.util.Const.FAVORITE_CHARACTERS_TABLE

@Entity(tableName = FAVORITE_CHARACTERS_TABLE)
data class CharacterEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val imageUrl: String,
)
