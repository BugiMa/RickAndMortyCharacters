package com.bugima.rickandmortycharacters.data.remote.dto

import com.google.gson.annotations.SerializedName

data class ResponseInfoDto(
    @SerializedName("pages") val pageCount: Int,
)
