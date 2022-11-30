package agency.five.codebase.android.movieapp.ui.home

import agency.five.codebase.android.movieapp.data.repository.MovieRepository
import agency.five.codebase.android.movieapp.model.MovieCategory
import agency.five.codebase.android.movieapp.ui.home.mapper.HomeScreenMapper
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeScreenViewModel(
    private val movieRepository: MovieRepository,
    private val homeMapper: HomeScreenMapper
) : ViewModel() {

    private val popular = listOf(
        MovieCategory.POPULAR_STREAMING,
        MovieCategory.POPULAR_ON_TV,
        MovieCategory.POPULAR_FOR_RENT,
        MovieCategory.POPULAR_IN_THEATRES
    )
    private val nowPlaying = listOf(
        MovieCategory.NOW_PLAYING_MOVIES,
        MovieCategory.NOW_PLAYING_TV
    )
    private val upcoming = listOf(
        MovieCategory.UPCOMING_TODAY,
        MovieCategory.UPCOMING_THIS_WEEK
    )

    private val popularSelected = MutableStateFlow(MovieCategory.POPULAR_STREAMING)
    private val nowPlayingSelected = MutableStateFlow(MovieCategory.NOW_PLAYING_MOVIES)
    private val upcomingSelected = MutableStateFlow(MovieCategory.UPCOMING_TODAY)

    private val initialHomeMovieCategoryViewState =
        HomeMovieCategoryViewState(emptyList(), emptyList())

    private val _popularCategoryViewState = MutableStateFlow(initialHomeMovieCategoryViewState)
    val popularCategoryViewState = _popularCategoryViewState.asStateFlow()

    private val _nowPlayingCategoryViewState = MutableStateFlow(initialHomeMovieCategoryViewState)
    val nowPlayingCategoryViewState = _nowPlayingCategoryViewState.asStateFlow()

    private val _upcomingCategoryViewState = MutableStateFlow(initialHomeMovieCategoryViewState)
    val upcomingCategoryViewState = _upcomingCategoryViewState.asStateFlow()

    init {
        getPopularMovies()
        getNowPlayingMovies()
        getUpcomingMovies()
    }

    private fun getPopularMovies() {
        viewModelScope.launch {
            movieRepository.popularMovies(MovieCategory.POPULAR_STREAMING).collect { movies ->
                _popularCategoryViewState.value = homeMapper.toHomeMovieCategoryViewState(
                    popular,
                    popularSelected.value,
                    movies
                )
            }
        }
    }

    private fun getNowPlayingMovies() {
        viewModelScope.launch {
            movieRepository.nowPlayingMovies(MovieCategory.NOW_PLAYING_TV).collect { movies ->
                _nowPlayingCategoryViewState.value = homeMapper.toHomeMovieCategoryViewState(
                    nowPlaying,
                    nowPlayingSelected.value,
                    movies
                )
            }
        }
    }

    private fun getUpcomingMovies() {
        viewModelScope.launch {
            movieRepository.upcomingMovies(MovieCategory.UPCOMING_TODAY).collect { movies ->
                _upcomingCategoryViewState.value = homeMapper.toHomeMovieCategoryViewState(
                    upcoming,
                    upcomingSelected.value,
                    movies
                )
            }
        }
    }

    fun switchSelectedCategory(categoryId: Int) {
        when (categoryId) {
            MovieCategory.POPULAR_STREAMING.ordinal,
            MovieCategory.POPULAR_ON_TV.ordinal,
            MovieCategory.POPULAR_FOR_RENT.ordinal,
            MovieCategory.POPULAR_IN_THEATRES.ordinal
            -> {
                popularSelected.update { MovieCategory.values()[categoryId] }
            }
            MovieCategory.NOW_PLAYING_MOVIES.ordinal,
            MovieCategory.NOW_PLAYING_TV.ordinal
            -> {
                nowPlayingSelected.update { MovieCategory.values()[categoryId] }
            }
            else -> {
                upcomingSelected.update { MovieCategory.values()[categoryId] }
            }
        }
    }
}
