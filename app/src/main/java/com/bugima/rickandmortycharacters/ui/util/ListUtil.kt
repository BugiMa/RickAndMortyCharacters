package com.bugima.rickandmortycharacters.ui.util

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

@Composable
inline fun LazyListState.ScrollEndCallback(crossinline callback: () -> Unit) {
    LaunchedEffect(this) {
        snapshotFlow { layoutInfo }
            .filter { it.totalItemsCount > 0 }
            .map { layoutInfo ->
                layoutInfo.totalItemsCount == (layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: -1) + 1
            }
            .distinctUntilChanged()
            .filter { it }
            .collect { callback() }
    }
}
