package com.bugima.rickandmortycharacters.data.remote.dto

import com.google.gson.annotations.SerializedName

data class PagedCharactersResponseDto(
    @SerializedName("info") val info: ResponseInfoDto,
    @SerializedName("results") val characters: List<CharacterDto>,
)
