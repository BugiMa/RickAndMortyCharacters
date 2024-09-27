package com.bugima.rickandmortycharacters.data.remote.api

import com.bugima.rickandmortycharacters.data.remote.dto.PagedCharactersResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RickAndMortyApi {
    @GET("character/")
    suspend fun getCharacters(
        @Query("page") page: Int
    ): Response<PagedCharactersResponseDto>
}
