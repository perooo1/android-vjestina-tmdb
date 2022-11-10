package agency.five.codebase.android.movieapp.ui.component

import agency.five.codebase.android.movieapp.R
import agency.five.codebase.android.movieapp.ui.theme.spacing
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun FavoriteButton(
    isFavorite: Boolean = false,
    modifier: Modifier = Modifier,
    onCLick: () -> Unit
) {
    Image(
        painter = painterResource(
            id = if (isFavorite) R.drawable.ic_baseline_favorite_24_filled_white else R.drawable.ic_baseline_favorite_24_border_white
        ),
        contentDescription = null,
        modifier = modifier
            .size(32.dp)
            .clickable { onCLick() }
            .background(
                color = Color.Gray.copy(alpha = 0.5f),
                shape = CircleShape
            )
            .padding(MaterialTheme.spacing.btn_favorite_icon_padding),
        contentScale = ContentScale.Crop
    )
}

@Preview(showBackground = true)
@Composable
fun FavoriteButtonPreview() {
    var isFavorite by rememberSaveable { mutableStateOf(true) }

    FavoriteButton(isFavorite) { isFavorite = !isFavorite }
}
