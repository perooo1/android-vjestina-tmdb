package agency.five.codebase.android.movieapp.ui.home

import agency.five.codebase.android.movieapp.R
import agency.five.codebase.android.movieapp.mock.MoviesMock
import agency.five.codebase.android.movieapp.model.MovieCategory
import agency.five.codebase.android.movieapp.ui.component.MovieCard
import agency.five.codebase.android.movieapp.ui.component.MovieCategoryLabel
import agency.five.codebase.android.movieapp.ui.component.MovieViewState
import agency.five.codebase.android.movieapp.ui.home.mapper.HomeScreenMapper
import agency.five.codebase.android.movieapp.ui.home.mapper.HomeScreenMapperImpl
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
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview

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

val popularCategoryViewState = mutableStateOf(
    homeMapper.toHomeMovieCategoryViewState(
        popular,
        MovieCategory.POPULAR_STREAMING,
        movies
    )
)
val nowPlayingCategoryViewState = mutableStateOf(
    homeMapper.toHomeMovieCategoryViewState(
        nowPlaying,
        MovieCategory.NOW_PLAYING_MOVIES,
        movies
    )
)
val upcomingCategoryViewState = mutableStateOf(
    homeMapper.toHomeMovieCategoryViewState(
        upcoming,
        MovieCategory.UPCOMING_TODAY,
        movies
    )
)

@Composable
fun HomeScreenRoute(
    onNavigateToMovieDetails: (Int) -> Unit
) {
    val popularCategory by remember {
        popularCategoryViewState
    }
    val nowPlayingCategory by remember {
        nowPlayingCategoryViewState
    }
    val upcomingCategory by remember {
        upcomingCategoryViewState
    }
    HomeScreen(
        popular = popularCategory,
        nowPlaying = nowPlayingCategory,
        upcoming = upcomingCategory,
        onNavigateToMovieDetails = onNavigateToMovieDetails,
        onCategoryClick = { categoryId ->
            switchSelectedCategory(categoryId)
        }
    )
}

@Composable
fun HomeScreen(
    popular: HomeMovieCategoryViewState,
    nowPlaying: HomeMovieCategoryViewState,
    upcoming: HomeMovieCategoryViewState,
    onNavigateToMovieDetails: (Int) -> Unit,
    onCategoryClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = modifier
            .verticalScroll(scrollState)
    ) {
        HomeScreenSection(
            viewState = popular,
            headerTitle = stringResource(id = R.string.whats_popular),
            onNavigateToMovieDetails = onNavigateToMovieDetails,
            onCategoryClick = onCategoryClick
        )
        HomeScreenSection(
            viewState = nowPlaying,
            headerTitle = stringResource(id = R.string.now_playing),
            onNavigateToMovieDetails = onNavigateToMovieDetails,
            onCategoryClick = onCategoryClick
        )
        HomeScreenSection(
            viewState = upcoming,
            headerTitle = stringResource(id = R.string.upcoming),
            onNavigateToMovieDetails = onNavigateToMovieDetails,
            onCategoryClick = onCategoryClick
        )
    }
}

@Composable
fun HomeScreenSection(
    viewState: HomeMovieCategoryViewState,
    headerTitle: String,
    onNavigateToMovieDetails: (Int) -> Unit,
    onCategoryClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        HomeScreenSectionHeader(
            headerTitle,
            Modifier.padding(start = MaterialTheme.spacing.medium)
        )
        Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
        LazyRow(
            horizontalArrangement =
            Arrangement.spacedBy(MaterialTheme.spacing.spacer_favorites_screen_header_text_list),
            contentPadding = PaddingValues(
                start = MaterialTheme.spacing.medium,
                end = MaterialTheme.spacing.medium
            )
        ) {
            items(
                items = viewState.movieCategories,
                key = { category ->
                    category.itemId
                }
            ) { category ->
                MovieCategoryLabel(
                    labelViewState = category,
                    onClick = { onCategoryClick(category.itemId) }
                )
            }
        }
        Spacer(
            modifier =
            Modifier.height(MaterialTheme.spacing.spacer_favorites_screen_header_text_list)
        )
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small),
            contentPadding = PaddingValues(
                start = MaterialTheme.spacing.medium,
                end = MaterialTheme.spacing.medium
            )
        ) {
            items(
                items = viewState.movies,
                key = { movie ->
                    movie.id
                }
            ) { movie ->
                MovieCard(
                    movieViewState = MovieViewState(
                        movie.movieViewState.imageUrl,
                        movie.movieViewState.isFavorite
                    ),
                    onFavouriteButtonClick = { /*TODO*/ },
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
    headerTitle: String,
    modifier: Modifier = Modifier
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

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    MovieAppTheme {
        HomeScreen(
            popular = popularCategoryViewState.value,
            nowPlaying = nowPlayingCategoryViewState.value,
            upcoming = upcomingCategoryViewState.value,
            onNavigateToMovieDetails = {},
            onCategoryClick = {}
        )
    }
}

fun switchSelectedCategory(categoryId: Int) {
    when (categoryId) {
        MovieCategory.POPULAR_STREAMING.ordinal,
        MovieCategory.POPULAR_ON_TV.ordinal,
        MovieCategory.POPULAR_FOR_RENT.ordinal,
        MovieCategory.POPULAR_IN_THEATRES.ordinal
        -> {
            popularCategoryViewState.value = homeMapper.toHomeMovieCategoryViewState(
                popular,
                MovieCategory.values()[categoryId],
                movies
            )
        }
        MovieCategory.NOW_PLAYING_MOVIES.ordinal,
        MovieCategory.NOW_PLAYING_TV.ordinal
        -> {
            nowPlayingCategoryViewState.value = homeMapper.toHomeMovieCategoryViewState(
                nowPlaying,
                MovieCategory.values()[categoryId],
                movies
            )
        }
        else -> {
            upcomingCategoryViewState.value = homeMapper.toHomeMovieCategoryViewState(
                upcoming,
                MovieCategory.values()[categoryId],
                movies
            )
        }
    }
}
