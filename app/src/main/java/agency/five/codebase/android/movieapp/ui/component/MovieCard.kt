package agency.five.codebase.android.movieapp.ui.component

import agency.five.codebase.android.movieapp.R
import agency.five.codebase.android.movieapp.mock.MoviesMock
import agency.five.codebase.android.movieapp.ui.theme.LocalSpacing
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

data class MovieViewState(
    val imageUrl: String?,
    val isFavorite: MutableState<Boolean>
)

@Composable
fun MovieCard(
    modifier: Modifier = Modifier
        .size(
            width = 122.dp,
            height = 179.dp
        ),
    movieViewState: MovieViewState,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .clickable { onClick },
        shape = RoundedCornerShape(dimensionResource(id = R.dimen.card_corner_radius)),
        elevation = dimensionResource(id = R.dimen.card_elevation)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.TopStart
        ) {
            AsyncImage(
                model = movieViewState.imageUrl,
                placeholder = painterResource(id = R.drawable.ic_launcher_background),
                contentDescription = stringResource(R.string.movie_image),
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            FavoriteButton(
                modifier = Modifier
                    .padding(
                        start = LocalSpacing.current.small,
                        top = LocalSpacing.current.small
                    ),
                isFavorite = movieViewState.isFavorite.value
            ) {
                movieViewState.isFavorite.value = it
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MovieCardPreview() {
    val movie = MoviesMock.getMovieDetails().movie
    val viewState = MovieViewState(
        imageUrl = movie.imageUrl,
        isFavorite = rememberSaveable {
            mutableStateOf(movie.isFavorite)
        }
    )
    MovieCard(movieViewState = viewState, onClick = {})
}
