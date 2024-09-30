package com.bugima.rickandmortycharacters.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.bugima.rickandmortycharacters.R
import com.bugima.rickandmortycharacters.ui.component.CharacterListItem
import com.bugima.rickandmortycharacters.ui.component.CommonCircularProgressIndicator
import com.bugima.rickandmortycharacters.ui.component.CommonIconToggle
import com.bugima.rickandmortycharacters.ui.component.CommonSpinningIconButton
import com.bugima.rickandmortycharacters.ui.theme.RickAndMortyCharactersTheme
import com.bugima.rickandmortycharacters.ui.util.ScrollEndCallback
import com.bugima.rickandmortycharacters.ui.util.visible
import com.bugima.rickandmortycharacters.util.Const
import com.bugima.rickandmortycharacters.util.CustomException
import com.bugima.rickandmortycharacters.util.Resource

@Composable
fun MainScreen(
    state: MainScreenViewState,
    onNewEvent: (MainScreenEvent) -> Unit
) {
    RickAndMortyCharactersTheme(darkTheme = state.isDarkModeEnabled) {
        Scaffold(
            containerColor = MaterialTheme.colorScheme.background,
            topBar = {
                Toolbar(
                    isFavoriteFilterEnabled = state.isFavoritesFilterEnabled,
                    isDarkModeEnabled = state.isDarkModeEnabled,
                    isError = state.characterLists is Resource.Error,
                    onFilterClicked = { onNewEvent(MainScreenEvent.ToggleFavoriteFilter) },
                    onThemeClicked = { onNewEvent(MainScreenEvent.ToggleDarkMode) }
                )
            },
            modifier = Modifier.statusBarsPadding()
        ) { contentPadding ->

            when (state.characterLists) {
                is Resource.Loading -> {
                    LoadingContent()
                }

                is Resource.Success -> {
                    SuccessContent(
                        characterLists = state.characterLists.data,
                        isFavoriteFilterEnabled = state.isFavoritesFilterEnabled,
                        onNewEvent = onNewEvent,
                        contentPadding = contentPadding
                    )
                }

                is Resource.Error -> {
                    ErrorContent(error = state.characterLists.error) {
                        onNewEvent(MainScreenEvent.Initialize)
                    }
                }
            }
        }
    }
}

@Composable
fun LoadingContent() {
    CommonCircularProgressIndicator()
}

@Composable
fun SuccessContent(
    characterLists: CharacterLists,
    isFavoriteFilterEnabled: Boolean,
    onNewEvent: (MainScreenEvent) -> Unit,
    contentPadding: PaddingValues
) {
    val allCharactersScrollState = rememberLazyListState()
        .also { it.ScrollEndCallback { onNewEvent(MainScreenEvent.LoadMoreCharacters) } }
    val favoriteCharactersScrollState = rememberLazyListState()

    if (isFavoriteFilterEnabled && characterLists.favoriteCharacters.isEmpty()) {
        EmptyFavoritesInfo()
    }

    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(
            top = contentPadding.calculateTopPadding() + 8.dp,
            bottom = contentPadding.calculateBottomPadding() + 8.dp
        ),
        state = if (isFavoriteFilterEnabled)
            favoriteCharactersScrollState else allCharactersScrollState,
        modifier = Modifier.padding(horizontal = 8.dp)
    ) {
        items(
            items = if (isFavoriteFilterEnabled)
                characterLists.favoriteCharacters else characterLists.allCharacters
        ) { character ->
            CharacterListItem(
                character = character
            ) { onNewEvent(MainScreenEvent.ToggleFavoriteCharacter(character)) }
        }
    }
}

@Composable
fun EmptyFavoritesInfo() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                imageVector = Icons.Rounded.FavoriteBorder,
                contentDescription = null,
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
                modifier = Modifier.size(64.dp),
            )
            Text(
                text = Const.NO_FAVORITES_TITLE,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = Const.NO_FAVORITES_DESCRIPTION,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}

@Composable
fun ErrorContent(error: CustomException, onRefreshClicked: () -> Unit) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.errorContainer)
            .padding(16.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(error.iconResourceId),
                contentDescription = error.title,
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.error),
                modifier = Modifier.size(64.dp),
            )
            Text(
                text = error.title,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onErrorContainer
            )
            Text(
                text = error.description,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onErrorContainer
            )
            if (error is CustomException.NetworkException) {
                CommonSpinningIconButton(
                    icon = Icons.Rounded.Refresh,
                    color = MaterialTheme.colorScheme.error,
                    iconColor = MaterialTheme.colorScheme.onErrorContainer
                ) { onRefreshClicked() }
            }
        }
    }
}

@Composable
fun Toolbar(
    isFavoriteFilterEnabled: Boolean,
    isDarkModeEnabled: Boolean,
    isError: Boolean,
    onFilterClicked: () -> Unit,
    onThemeClicked: () -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .background(color = MaterialTheme.colorScheme.surface)
            .padding(8.dp)
    ) {
        CommonIconToggle(
            icon = Icons.Rounded.Favorite,
            isToggled = isFavoriteFilterEnabled,
            modifier = Modifier.visible(!isError)
        ) { onFilterClicked() }

        Text(
            text = if (isFavoriteFilterEnabled) Const.SCREEN_FAV else Const.SCREEN_ALL,
            style = MaterialTheme.typography.titleLarge
        )

        CommonIconToggle(
            icon = ImageVector.vectorResource(
                id = if (isDarkModeEnabled)
                    R.drawable.icon_light_mode else R.drawable.icon_dark_mode
            ),
            isToggled = isDarkModeEnabled
        ) { onThemeClicked() }
    }
}
