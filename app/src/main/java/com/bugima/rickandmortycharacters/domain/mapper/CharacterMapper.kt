package com.bugima.rickandmortycharacters.domain.mapper

import com.bugima.rickandmortycharacters.data.local.entity.CharacterEntity
import com.bugima.rickandmortycharacters.data.remote.dto.CharacterDto
import com.bugima.rickandmortycharacters.domain.model.Character

fun CharacterDto.toDomain() = Character(
    id = id,
    name = name,
    imageUrl = imageUrl,
)

fun CharacterEntity.toDomain() = Character(
    id = id,
    name = name,
    imageUrl = imageUrl,
)

fun Character.toData() = CharacterEntity(
    id = id,
    name = name,
    imageUrl = imageUrl,
)
