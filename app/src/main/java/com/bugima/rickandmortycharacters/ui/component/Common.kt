package com.bugima.rickandmortycharacters.ui.component

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.bugima.rickandmortycharacters.util.Const

@Composable
fun CommonIconToggle(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    size: Dp = 32.dp,
    isToggled: Boolean,
    onClick: () -> Unit,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .size(size)
            .clip(shape = RoundedCornerShape(6.dp))
            .background(
                color = if (isToggled)
                    MaterialTheme.colorScheme.background else Color.Transparent,
            )
            .clickable { onClick() }

    ) {
        Image(
            imageVector = icon,
            contentDescription = null,
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
            modifier = Modifier.size(24.dp)
        )
    }
}

@Composable
fun CommonCircularProgressIndicator() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        CircularProgressIndicator(
            color = MaterialTheme.colorScheme.primary,
            strokeWidth = 8.dp,
            trackColor = MaterialTheme.colorScheme.background,
            modifier = Modifier.size(64.dp)
        )
    }
}

@Composable
fun CommonSpinningIconButton(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    size: Dp = 48.dp,
    color: Color,
    iconColor: Color,
    onClicked: () -> Unit
) {
    val angle = remember { mutableFloatStateOf(0f) }
    val animatedAngle by animateFloatAsState(
        targetValue = angle.floatValue,
        animationSpec = tween(durationMillis = Const.SPIN_DURATION),
        label = "spinningAnimation"
    )

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .size(size)
            .clip(shape = RoundedCornerShape(size / 2))
            .background(color)
            .clickable {
                angle.floatValue += Const.SPIN_DEGREE
                onClicked()
            }
    ) {
        Image(
            imageVector = icon,
            contentDescription = null,
            colorFilter = ColorFilter.tint(iconColor),
            modifier = Modifier
                .size(32.dp)
                .graphicsLayer(rotationZ = animatedAngle)
        )
    }
}