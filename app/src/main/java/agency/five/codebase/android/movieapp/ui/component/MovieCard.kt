package agency.five.codebase.android.movieapp.ui.component

import agency.five.codebase.android.movieapp.R
import agency.five.codebase.android.movieapp.mock.MoviesMock
import agency.five.codebase.android.movieapp.ui.theme.spacing
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage

data class MovieViewState(
    val imageUrl: String?,
    val isFavorite: Boolean
)

@Composable
fun MovieCard(
    movieViewState: MovieViewState,
    onFavouriteButtonClick: () -> Unit,
    onMovieCardClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .clickable { onMovieCardClick() },
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
                        start = MaterialTheme.spacing.small,
                        top = MaterialTheme.spacing.small
                    ),
                isFavorite = movieViewState.isFavorite,
                onCLick = { onFavouriteButtonClick() }
            )
        }
    }
}
/*
@Preview(showBackground = true)
@Composable
fun MovieCardPreview() {
    val movie = MoviesMock.getMovieDetails().movie
    val viewState = MovieViewState(
        imageUrl = movie.imageUrl,
        isFavorite = movie.isFavorite
    )
    MovieCard(
        movieViewState = viewState,
        onFavouriteButtonClick = {},
        onMovieCardClick = {}
    )
}
*/
