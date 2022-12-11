package agency.five.codebase.android.movieapp.ui.favorites

import agency.five.codebase.android.movieapp.R
import agency.five.codebase.android.movieapp.ui.component.MovieCard
import agency.five.codebase.android.movieapp.ui.component.MovieViewState
import agency.five.codebase.android.movieapp.ui.theme.HeaderText
import agency.five.codebase.android.movieapp.ui.theme.MovieAppTheme
import agency.five.codebase.android.movieapp.ui.theme.spacing
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import org.koin.androidx.compose.getViewModel

private const val FAVORITES_GRID_COLUMNS_COUNT = 3

@Composable
fun FavoritesRoute(
    viewModel: FavoritesViewModel,
    onNavigateToMovieDetails: (Int) -> Unit
) {
    val favoritesViewState: FavoritesViewState by viewModel.favoritesViewState.collectAsState()
    FavoritesScreen(favoritesViewState, viewModel::toggleFavorite, onNavigateToMovieDetails)
}

@Composable
fun FavoritesScreen(
    favoritesViewState: FavoritesViewState,
    onFavoriteButtonClick: (Int) -> Unit,
    onNavigateToMovieDetails: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(FAVORITES_GRID_COLUMNS_COUNT),
            contentPadding = PaddingValues(horizontal = MaterialTheme.spacing.medium),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium),
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)
        ) {
            item(
                span = {
                    GridItemSpan(maxLineSpan)
                }
            ) {
                FavoritesHeader()
            }
            items(
                items = favoritesViewState.favoriteMovies,
                key = { movie ->
                    movie.id
                }
            ) { movie ->
                MovieCard(
                    movieViewState = MovieViewState(
                        movie.movieViewState.imageUrl,
                        movie.movieViewState.isFavorite
                    ),
                    onFavouriteButtonClick = { onFavoriteButtonClick(movie.id) },
                    onMovieCardClick = { onNavigateToMovieDetails(movie.id) },
                    modifier = Modifier.size(
                        dimensionResource(id = R.dimen.movie_card_width),
                        dimensionResource(id = R.dimen.movie_card_height)
                    )
                )
            }
        }
    }
}

@Composable
fun FavoritesHeader() {
    Text(
        text = stringResource(id = R.string.favorites_text),
        style = HeaderText,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = MaterialTheme.spacing.large
            )
    )
    Spacer(
        modifier = Modifier
            .height(
                MaterialTheme.spacing.spacer_favorites_screen_header_text_list
            )
    )
}

@Preview(showBackground = true)
@Composable
fun FavoritesScreenPreview() {
    MovieAppTheme {
        FavoritesScreen(getViewModel(), {}, {})
    }
}
