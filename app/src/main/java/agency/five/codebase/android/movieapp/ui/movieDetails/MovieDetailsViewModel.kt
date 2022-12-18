package agency.five.codebase.android.movieapp.ui.movieDetails

import agency.five.codebase.android.movieapp.data.repository.MovieRepository
import agency.five.codebase.android.movieapp.ui.movieDetails.mapper.MovieDetailsMapper
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

private const val STOP_TIMEOUT_MILIS = 5000L

class MovieDetailsViewModel(
    private val movieId: Int,
    private val movieRepository: MovieRepository,
    private val movieDetailsMapper: MovieDetailsMapper
) : ViewModel() {

    private val initialViewState = MovieDetailsViewState(
        id = 1,
        imageUrl = null,
        voteAverage = 8.1f,
        title = "",
        overview = "",
        isFavorite = false,
        crew = emptyList(),
        cast = emptyList()
    )

    val movieDetailsViewState = movieRepository.movieDetails(movieId).map { details ->
        movieDetailsMapper.toMovieDetailsViewState(details)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(STOP_TIMEOUT_MILIS),
        initialValue = initialViewState
    )

    fun toggleFavorite(movieId: Int) {
        viewModelScope.launch {
            movieRepository.toggleFavorite(movieId)
        }
    }
}
