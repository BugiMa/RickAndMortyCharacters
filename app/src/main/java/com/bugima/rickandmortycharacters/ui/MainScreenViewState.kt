package com.bugima.rickandmortycharacters.ui

import androidx.compose.runtime.Immutable
import com.bugima.rickandmortycharacters.util.Const
import com.bugima.rickandmortycharacters.util.Resource

@Immutable
data class MainScreenViewState(
    val characterLists: Resource<CharacterLists> = Resource.Loading,
    val nextPage: Int = Const.INITIAL_NEXT_PAGE,
    val isFavoritesFilterEnabled: Boolean = false,
    val isDarkModeEnabled: Boolean = false,
)

data class CharacterLists(
    val allCharacters: List<CharacterViewState>,
    val favoriteCharacters: List<CharacterViewState>,
)

data class CharacterViewState(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val isFavorite: Boolean,
)
