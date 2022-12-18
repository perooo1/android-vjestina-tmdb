package agency.five.codebase.android.movieapp.ui.favorites

import agency.five.codebase.android.movieapp.data.repository.MovieRepository
import agency.five.codebase.android.movieapp.ui.favorites.mapper.FavoritesMapper
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

private const val STOP_TIMEOUT_MILIS = 5000L

class FavoritesViewModel(
    private val movieRepository: MovieRepository, private val favoritesMapper: FavoritesMapper
) : ViewModel() {

    val favoritesViewState: StateFlow<FavoritesViewState> =
        movieRepository.favoriteMovies().map { movies ->
            favoritesMapper.toFavoritesViewState(movies)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(STOP_TIMEOUT_MILIS),
            initialValue = FavoritesViewState(
                emptyList()
            )
        )

    fun toggleFavorite(movieId: Int) {
        viewModelScope.launch {
            movieRepository.removeMovieFromFavorites(movieId)
        }
    }
}
