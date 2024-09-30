package com.bugima.rickandmortycharacters.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = Accent,
    onPrimary = LightPrimary,
    primaryContainer = AccentDark,
    onPrimaryContainer = AccentLight,
    background = DarkPrimary,
    onBackground = LightPrimary,
    surface = DarkSecondary,
    onSurface = LightPrimary,
    error = Error,
    onError = LightPrimary,
    errorContainer = ErrorDark,
    onErrorContainer = ErrorLight,
)

private val LightColorScheme = lightColorScheme(
    primary = Accent,
    onPrimary = DarkPrimary,
    primaryContainer = AccentLight,
    onPrimaryContainer = AccentDark,
    background = LightSecondary,
    onBackground = DarkPrimary,
    surface = LightPrimary,
    onSurface = DarkSecondary,
    error = Error,
    onError = LightPrimary,
    errorContainer = ErrorLight,
    onErrorContainer = ErrorDark,
)

@Composable
fun RickAndMortyCharactersTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
