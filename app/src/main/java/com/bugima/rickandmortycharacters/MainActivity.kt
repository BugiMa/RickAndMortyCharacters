package com.bugima.rickandmortycharacters

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bugima.rickandmortycharacters.ui.MainScreen
import com.bugima.rickandmortycharacters.ui.MainScreenEvent
import com.bugima.rickandmortycharacters.ui.theme.RickAndMortyCharactersTheme
import com.bugima.rickandmortycharacters.ui.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint(ComponentActivity::class)
class MainActivity : ComponentActivity() {
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel.onNewEvent(MainScreenEvent.Initialize)
        setContent {
            val state = mainViewModel.state.collectAsStateWithLifecycle()

            RickAndMortyCharactersTheme {
                MainScreen(
                    state = state.value,
                    onNewEvent = { event -> mainViewModel.onNewEvent(event) }
                )
            }
        }
    }
}
