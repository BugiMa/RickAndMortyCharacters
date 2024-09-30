package com.bugima.rickandmortycharacters.util

sealed interface Resource<out T> {
    data class Success<out T>(val data: T) : Resource<T>
    data class Error(val error: CustomException) : Resource<Nothing>
    data object Loading : Resource<Nothing>
}
