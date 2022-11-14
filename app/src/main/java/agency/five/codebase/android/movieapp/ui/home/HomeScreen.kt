package agency.five.codebase.android.movieapp.ui.home

import agency.five.codebase.android.movieapp.R
import agency.five.codebase.android.movieapp.mock.MoviesMock
import agency.five.codebase.android.movieapp.model.MovieCategory
import agency.five.codebase.android.movieapp.ui.component.MovieCard
import agency.five.codebase.android.movieapp.ui.component.MovieCategoryLabel
import agency.five.codebase.android.movieapp.ui.component.MovieViewState
import agency.five.codebase.android.movieapp.ui.home.mapper.HomeScreenMapper
import agency.five.codebase.android.movieapp.ui.home.mapper.HomeScreenMapperImpl
import agency.five.codebase.android.movieapp.ui.movieDetails.MovieDetailsScreen
import agency.five.codebase.android.movieapp.ui.theme.HeaderText
import agency.five.codebase.android.movieapp.ui.theme.MovieAppTheme
import agency.five.codebase.android.movieapp.ui.theme.spacing
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

val homeMapper: HomeScreenMapper = HomeScreenMapperImpl()
val movies = MoviesMock.getMoviesList()

val popular = listOf(
    MovieCategory.POPULAR_STREAMING,
    MovieCategory.POPULAR_ON_TV,
    MovieCategory.POPULAR_FOR_RENT,
    MovieCategory.POPULAR_IN_THEATRES
)
val nowPlaying = listOf(
    MovieCategory.NOW_PLAYING_MOVIES,
    MovieCategory.NOW_PLAYING_TV
)
val upcoming = listOf(
    MovieCategory.UPCOMING_TODAY,
    MovieCategory.UPCOMING_THIS_WEEK
)
val popularCategoryViewState =
    homeMapper.toHomeMovieCategoryViewState(popular, MovieCategory.POPULAR_STREAMING, movies)
val nowPlayingCategoryViewState =
    homeMapper.toHomeMovieCategoryViewState(nowPlaying, MovieCategory.NOW_PLAYING_MOVIES, movies)
val upcomingCategoryViewState =
    homeMapper.toHomeMovieCategoryViewState(upcoming, MovieCategory.UPCOMING_TODAY, movies)

@Composable
fun HomeScreenRoute() {
    //TODO
}

@Composable
fun HomeScreen(
    popular: HomeMovieCategoryViewState,
    nowPlaying: HomeMovieCategoryViewState,
    upcoming: HomeMovieCategoryViewState,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

    Column(modifier = modifier.verticalScroll(scrollState)) {
        HomeScreenSection(
            viewState = popular,
            headerTitle = stringResource(id = R.string.whats_popular)
        )
        HomeScreenSection(
            viewState = nowPlaying,
            headerTitle = stringResource(id = R.string.now_playing)
        )
        HomeScreenSection(
            viewState = upcoming,
            headerTitle = stringResource(id = R.string.upcoming)
        )
    }

}

@Composable
fun HomeScreenSection(
    viewState: HomeMovieCategoryViewState,
    headerTitle: String,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        HomeScreenSectionHeader(headerTitle)
        Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
        LazyRow(
            horizontalArrangement =
            Arrangement.spacedBy(MaterialTheme.spacing.spacer_favorites_screen_header_text_list)
        ) {
            items(
                items = viewState.movieCategories,
                key = { category ->
                    category.itemId
                }
            ) { category ->
                MovieCategoryLabel(
                    labelViewState = category,
                    onClick = { /*TODO*/ }
                )
            }
        }
        Spacer(
            modifier =
            Modifier.height(MaterialTheme.spacing.spacer_favorites_screen_header_text_list)
        )
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)
        ) {
            items(
                items = viewState.movies,
                key = { movie ->
                    movie.id
                }
            ) { movie ->
                MovieCard(
                    movieViewState = MovieViewState(movie.imageUrl, movie.isFavorite),
                    onFavouriteButtonClick = { /*TODO*/ },
                    onMovieCardClick = { /*TODO*/ },
                    modifier = Modifier.size(122.dp, 179.dp)
                )
            }
        }
    }
}

@Composable
fun HomeScreenSectionHeader(
    headerTitle: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = headerTitle,
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
fun HomeScreenSectionHeaderPreview() {
    HomeScreenSectionHeader(stringResource(id = R.string.now_playing))
}

@Preview(showBackground = true)
@Composable
fun HomeScreenSectionPreview() {
    MovieAppTheme() {
        HomeScreenSection(popularCategoryViewState, stringResource(id = R.string.today))
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    MovieAppTheme() {
        HomeScreen(
            popular = popularCategoryViewState,
            nowPlaying = nowPlayingCategoryViewState,
            upcoming = upcomingCategoryViewState
        )
    }
}

