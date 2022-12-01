package agency.five.codebase.android.movieapp.ui.movieDetails

import agency.five.codebase.android.movieapp.data.repository.MovieRepository
import agency.five.codebase.android.movieapp.ui.movieDetails.mapper.MovieDetailsMapper
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

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

    private val _movieDetailsViewState = MutableStateFlow(initialViewState)
    val movieDetailsViewState = _movieDetailsViewState.asStateFlow()

    init {
        viewModelScope.launch {
            movieRepository.movieDetails(movieId).collect { details ->
                _movieDetailsViewState.value = movieDetailsMapper.toMovieDetailsViewState(details)
            }
        }
    }

    fun toggleFavorite(movieId: Int){
        viewModelScope.launch {
            movieRepository.toggleFavorite(movieId)
        }
    }
}
