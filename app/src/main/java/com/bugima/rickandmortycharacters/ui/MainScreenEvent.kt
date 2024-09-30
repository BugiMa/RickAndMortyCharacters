package com.bugima.rickandmortycharacters.ui

sealed interface MainScreenEvent {
    data object Initialize: MainScreenEvent
    data object LoadMoreCharacters: MainScreenEvent
    data object RefreshCharacters: MainScreenEvent
    data class ToggleFavoriteCharacter(val character: CharacterViewState): MainScreenEvent
    data object ToggleFavoriteFilter: MainScreenEvent
    data object ToggleDarkMode: MainScreenEvent
}
