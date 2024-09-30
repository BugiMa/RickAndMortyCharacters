package com.bugima.rickandmortycharacters.ui.component

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import coil.size.Size
import com.bugima.rickandmortycharacters.ui.CharacterViewState
import com.bugima.rickandmortycharacters.util.Const

@Composable
fun CharacterListItem(
    character: CharacterViewState,
    onFavoriteClick: (Int) -> Unit,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(128.dp)
            .clip(shape = RoundedCornerShape(8.dp))
            .background(color = MaterialTheme.colorScheme.surface)
            .clickable { onFavoriteClick(character.id) }
            .padding(start = 8.dp, top = 8.dp, end = 16.dp, bottom = 8.dp)
    ) {
        SubcomposeAsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(character.imageUrl)
                .size(size = Size.ORIGINAL)
                .crossfade(enable = true)
                .build(),
            contentDescription = null,
            loading = { CommonCircularProgressIndicator() },
            error = { },
            modifier = Modifier.clip(shape = RoundedCornerShape(4.dp))
        )
        Text(
            text = character.name,
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(all = 8.dp),
        )
        Image(
            imageVector = if (character.isFavorite)
                Icons.Rounded.Favorite else Icons.Rounded.FavoriteBorder,
            contentDescription = null,
            colorFilter = ColorFilter.tint(
                if (character.isFavorite)
                    MaterialTheme.colorScheme.primary
                else
                    MaterialTheme.colorScheme.onSurface
            ),
            modifier = Modifier
                .size(24.dp)
                .align(Alignment.Top)
                .offset(y = 8.dp)
        )
    }
}
