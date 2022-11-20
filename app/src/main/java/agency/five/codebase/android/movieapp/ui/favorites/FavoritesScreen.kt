package agency.five.codebase.android.movieapp.ui.favorites

import agency.five.codebase.android.movieapp.R
import agency.five.codebase.android.movieapp.mock.MoviesMock
import agency.five.codebase.android.movieapp.ui.component.MovieCard
import agency.five.codebase.android.movieapp.ui.component.MovieViewState
import agency.five.codebase.android.movieapp.ui.favorites.mapper.FavoritesMapper
import agency.five.codebase.android.movieapp.ui.favorites.mapper.FavoritesMapperImpl
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview

private val favoritesMapper: FavoritesMapper = FavoritesMapperImpl()

val movies = MoviesMock.getMoviesList()
val favoriteMovies = movies.filter { it.isFavorite }

val favoritesViewState = favoritesMapper.toFavoritesViewState(favoriteMovies)

@Composable
fun FavoritesRoute(onNavigateToMovieDetails: (Int) -> Unit) {
    val favorites by remember {
        mutableStateOf(favoritesViewState)
    }
    FavoritesScreen(favorites, onNavigateToMovieDetails)
}

@Composable
fun FavoritesScreen(
    favoritesViewState: FavoritesViewState,
    onNavigateToMovieDetails: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(count = 3),
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
                    movieViewState = MovieViewState(movie.imageUrl, movie.isFavorite),
                    onFavouriteButtonClick = { },
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
    val viewState: FavoritesViewState by remember {
        mutableStateOf(favoritesViewState)
    }
    MovieAppTheme {
        FavoritesScreen(viewState, {})
    }
}
