package agency.five.codebase.android.movieapp.ui.component

import agency.five.codebase.android.movieapp.R
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
) {

    var isFavorite by rememberSaveable { mutableStateOf(true) }

    Image(
        painter = painterResource(
            id = if (isFavorite) R.drawable.ic_baseline_favorite_24_filled_white else R.drawable.ic_baseline_favorite_24_border_white
        ),
        contentDescription = null,

        modifier = Modifier
            .clickable { isFavorite = !isFavorite }
            .size(32.dp)
            .background(color = Color.Blue, shape = CircleShape)
            .padding(5.dp),
        contentScale = ContentScale.Crop
    )

}

@Preview(showBackground = true)
@Composable
fun FavoriteButtonPreview() {
    FavoriteButton()
}