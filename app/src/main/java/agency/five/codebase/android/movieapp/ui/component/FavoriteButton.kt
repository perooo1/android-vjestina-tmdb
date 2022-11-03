package agency.five.codebase.android.movieapp.ui.component

import agency.five.codebase.android.movieapp.R
import agency.five.codebase.android.movieapp.ui.theme.LocalSpacing
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun FavoriteButton(
    modifier: Modifier = Modifier
        .size(32.dp),
    isFavorite: Boolean = false,
    onCLick: (Boolean) -> Unit
) {
    Image(
        painter = painterResource(
            id = if (isFavorite) R.drawable.ic_baseline_favorite_24_filled_white else R.drawable.ic_baseline_favorite_24_border_white
        ),
        contentDescription = null,
        modifier = modifier
            .clickable { onCLick(isFavorite.not()) }
            .background(
                color = Color.Gray.copy(alpha = 0.5f),
                shape = CircleShape
            )
            .padding(LocalSpacing.current.btn_favorite_icon_padding),
        contentScale = ContentScale.Crop
    )
}

@Preview(showBackground = true)
@Composable
fun FavoriteButtonPreview() {
    var isFavorite by rememberSaveable { mutableStateOf(true) }

    FavoriteButton(isFavorite = isFavorite) {
        isFavorite = it
    }
}
