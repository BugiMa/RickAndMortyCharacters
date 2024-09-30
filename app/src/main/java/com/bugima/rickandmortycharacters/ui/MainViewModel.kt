package com.bugima.rickandmortycharacters.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.util.copy
import com.bugima.rickandmortycharacters.domain.model.Character
import com.bugima.rickandmortycharacters.domain.usecase.AddCharacterToFavoritesUseCase
import com.bugima.rickandmortycharacters.domain.usecase.FetchCharactersUseCase
import com.bugima.rickandmortycharacters.domain.usecase.GetAllFavoriteCharactersUseCase
import com.bugima.rickandmortycharacters.domain.usecase.GetCharacterPageCountUseCase
import com.bugima.rickandmortycharacters.domain.usecase.RemoveCharacterFromFavoritesUseCase
import com.bugima.rickandmortycharacters.ui.mapper.MainScreenViewMapperImpl
import com.bugima.rickandmortycharacters.util.Const
import com.bugima.rickandmortycharacters.util.Resource
import com.bugima.rickandmortycharacters.util.createLogMessage
import com.bugima.rickandmortycharacters.util.toCustomError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val fetchCharactersUseCase: FetchCharactersUseCase,
    private val getCharacterPageCountUseCase: GetCharacterPageCountUseCase,
    private val addCharacterToFavoritesUseCase: AddCharacterToFavoritesUseCase,
    private val removeCharacterFromFavoritesUseCase: RemoveCharacterFromFavoritesUseCase,
    private val getFavoriteCharacterUseCase: GetAllFavoriteCharactersUseCase,
    private val mainScreenViewMapper: MainScreenViewMapperImpl,
) : ViewModel() {

    private val _state = MutableStateFlow(MainScreenViewState())
    val state: StateFlow<MainScreenViewState>
        get() = _state.asStateFlow()

    fun onNewEvent(event: MainScreenEvent) {
        when (event) {
            MainScreenEvent.Initialize -> initialize()
            is MainScreenEvent.LoadMoreCharacters -> loadMoreCharacters()
            MainScreenEvent.RefreshCharacters -> refreshCharacters()
            MainScreenEvent.ToggleFavoriteFilter -> toggleFavoriteFilter()
            MainScreenEvent.ToggleDarkMode -> toggleDarkMode()
            is MainScreenEvent.ToggleFavoriteCharacter -> toggleFavoriteCharacter(event.character)
        }
    }

    private fun initialize() {
        viewModelScope.launch {
            try {
                val a = async(viewModelScope.coroutineContext) { getFavoriteCharacterUseCase() }
                val b =
                    async(viewModelScope.coroutineContext) { fetchCharactersUseCase(Const.INITIAL_PAGE) }
                awaitAll(a, b).let { (favoriteCharacters, fetchedCharacters) ->
                    val favoriteCharacterViewStates =
                        mainScreenViewMapper.mapToCharacterViewStateList(favoriteCharacters)

                    val fetchedCharacterViewStates =
                        mainScreenViewMapper.mapToCharacterViewStateList(
                            fetchedCharacters,
                            favoriteCharacters.map { it.id },
                        ).sortedBy { it.id }

                    _state.update { state ->
                        state.copy(
                            characterLists = Resource.Success(
                                CharacterLists(
                                    allCharacters = fetchedCharacterViewStates,
                                    favoriteCharacters = favoriteCharacterViewStates
                                )
                            ),
                        )
                    }
                }
            } catch (e: Exception) {
                Log.e(e::class.simpleName, e.createLogMessage())
                _state.update { it.copy(characterLists = Resource.Error(e.toCustomError())) }
            }
        }
    }

    private fun loadMoreCharacters() {
        getCharacterPageCountUseCase()?.let { pageCount ->
            if (_state.value.nextPage > pageCount) return
        }
        viewModelScope.launch(Dispatchers.IO) {
            try {
                fetchCharactersUseCase(state.value.nextPage).let { newCharacters ->
                    (_state.value.characterLists as? Resource.Success)?.let { characterLists ->

                        val currentCharacterLists = characterLists.data
                        val updatedCharacterLists = currentCharacterLists.copy(
                            allCharacters = currentCharacterLists.allCharacters +
                                    mainScreenViewMapper.mapToCharacterViewStateList(
                                        allCharacters = newCharacters,
                                        favoriteCharactersIds = currentCharacterLists.favoriteCharacters.map { it.id }
                                    ),
                        )

                        _state.update {
                            it.copy(
                                characterLists = Resource.Success(updatedCharacterLists),
                                nextPage = it.nextPage + 1,
                            )
                        }
                    }
                }
            } catch (e: Exception) {
                Log.e(e::class.simpleName, e.createLogMessage())
                _state.update { it.copy(characterLists = Resource.Error(e.toCustomError())) }
            }
        }
    }

    private fun refreshCharacters() {

    }

    private fun toggleFavoriteFilter() {
        _state.update { it.copy(isFavoritesFilterEnabled = !it.isFavoritesFilterEnabled) }
    }

    private fun toggleDarkMode() {
        _state.update { it.copy(isDarkModeEnabled = !it.isDarkModeEnabled) }
    }

    private fun toggleFavoriteCharacter(character: CharacterViewState) {
        viewModelScope.launch {
            try {
                if (character.isFavorite) {
                    removeCharacterFromFavoritesUseCase(character.toDomain())
                } else {
                    addCharacterToFavoritesUseCase(character.toDomain())
                }
                updateCharacterListsState(character)
            } catch (e: Exception) {
                Log.e(e::class.simpleName, e.createLogMessage())
                _state.update { it.copy(characterLists = Resource.Error(e.toCustomError())) }
            }
        }
    }

    private fun updateCharacterListsState(character: CharacterViewState) {
        (_state.value.characterLists as? Resource.Success)?.let { characterLists ->
            val allCharacters = characterLists.data.allCharacters.toMutableList()
            val favoriteCharacters = characterLists.data.favoriteCharacters.toMutableList()

            allCharacters.indexOfFirst { it.id == character.id }
                .takeIf { it != -1 }
                ?.let { index ->
                    val updatedCharacter = allCharacters[index].copy(
                        isFavorite = !allCharacters[index].isFavorite
                    )
                    allCharacters[index] = updatedCharacter
                }

            if (favoriteCharacters.any { it.id == character.id })
                favoriteCharacters.remove(character)
            else
                favoriteCharacters.add(character.copy(isFavorite = !character.isFavorite))

            _state.update { state ->
                state.copy(
                    characterLists = Resource.Success(
                        data = characterLists.data.copy(
                            allCharacters = allCharacters,
                            favoriteCharacters = favoriteCharacters.sortedBy { it.id }
                        )
                    )
                )
            }
        }
    }

    private fun CharacterViewState.toDomain() = Character(
        id = id,
        name = name,
        imageUrl = imageUrl,
    )
}