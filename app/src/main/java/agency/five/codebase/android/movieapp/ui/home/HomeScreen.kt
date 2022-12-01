package agency.five.codebase.android.movieapp.ui.home

import agency.five.codebase.android.movieapp.R
import agency.five.codebase.android.movieapp.ui.component.MovieCard
import agency.five.codebase.android.movieapp.ui.component.MovieCategoryLabel
import agency.five.codebase.android.movieapp.ui.component.MovieViewState
import agency.five.codebase.android.movieapp.ui.theme.HeaderText
import agency.five.codebase.android.movieapp.ui.theme.spacing
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow

@Composable
fun HomeScreenRoute(
    homeScreenViewModel: HomeScreenViewModel,
    onNavigateToMovieDetails: (Int) -> Unit
) {

    val popularCategoryViewState:
            HomeMovieCategoryViewState by homeScreenViewModel.popularCategoryViewState.collectAsState()
    val nowPlayingCategoryViewState:
            HomeMovieCategoryViewState by homeScreenViewModel.nowPlayingCategoryViewState.collectAsState()
    val upcomingCategoryViewState:
            HomeMovieCategoryViewState by homeScreenViewModel.upcomingCategoryViewState.collectAsState()

    HomeScreen(popular = popularCategoryViewState,
        nowPlaying = nowPlayingCategoryViewState,
        upcoming = upcomingCategoryViewState,
        onNavigateToMovieDetails = onNavigateToMovieDetails,
        onFavoriteButtonClick = { movieId ->
            homeScreenViewModel.toggleFavorite(movieId)
        },
        onCategoryClick = { categoryId ->
            homeScreenViewModel.switchSelectedCategory(categoryId)
        })
}

@Composable
fun HomeScreen(
    popular: HomeMovieCategoryViewState,
    nowPlaying: HomeMovieCategoryViewState,
    upcoming: HomeMovieCategoryViewState,
    onNavigateToMovieDetails: (Int) -> Unit,
    onFavoriteButtonClick: (Int) -> Unit,
    onCategoryClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = modifier.verticalScroll(scrollState)
    ) {
        HomeScreenSection(
            viewState = popular,
            headerTitle = stringResource(id = R.string.whats_popular),
            onNavigateToMovieDetails = onNavigateToMovieDetails,
            onFavoriteButtonClick = onFavoriteButtonClick,
            onCategoryClick = onCategoryClick
        )
        HomeScreenSection(
            viewState = nowPlaying,
            headerTitle = stringResource(id = R.string.now_playing),
            onNavigateToMovieDetails = onNavigateToMovieDetails,
            onFavoriteButtonClick = onFavoriteButtonClick,
            onCategoryClick = onCategoryClick
        )
        HomeScreenSection(
            viewState = upcoming,
            headerTitle = stringResource(id = R.string.upcoming),
            onNavigateToMovieDetails = onNavigateToMovieDetails,
            onFavoriteButtonClick = onFavoriteButtonClick,
            onCategoryClick = onCategoryClick
        )
    }
}

@Composable
fun HomeScreenSection(
    viewState: HomeMovieCategoryViewState,
    headerTitle: String,
    onNavigateToMovieDetails: (Int) -> Unit,
    onFavoriteButtonClick: (Int) -> Unit,
    onCategoryClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        HomeScreenSectionHeader(
            headerTitle, Modifier.padding(start = MaterialTheme.spacing.medium)
        )
        Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.spacer_favorites_screen_header_text_list),
            contentPadding = PaddingValues(
                start = MaterialTheme.spacing.medium, end = MaterialTheme.spacing.medium
            )
        ) {
            items(items = viewState.movieCategories, key = { category ->
                category.itemId
            }) { category ->
                MovieCategoryLabel(labelViewState = category,
                    onClick = { onCategoryClick(category.itemId) })
            }
        }
        Spacer(
            modifier = Modifier.height(MaterialTheme.spacing.spacer_favorites_screen_header_text_list)
        )
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small),
            contentPadding = PaddingValues(
                start = MaterialTheme.spacing.medium, end = MaterialTheme.spacing.medium
            )
        ) {
            items(items = viewState.movies, key = { movie ->
                movie.id
            }) { movie ->
                MovieCard(
                    movieViewState = MovieViewState(
                        movie.movieViewState.imageUrl, movie.movieViewState.isFavorite
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
fun HomeScreenSectionHeader(
    headerTitle: String, modifier: Modifier = Modifier
) {
    Text(
        text = headerTitle,
        style = HeaderText,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        modifier = modifier
            .fillMaxWidth()
            .padding(
                top = MaterialTheme.spacing.large
            )
    )
}
/*
@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    MovieAppTheme {
        HomeScreen(popular = popularCategoryViewState.value,
            nowPlaying = nowPlayingCategoryViewState.value,
            upcoming = upcomingCategoryViewState.value,
            onNavigateToMovieDetails = {},
            onCategoryClick = {})
    }
}
*/

