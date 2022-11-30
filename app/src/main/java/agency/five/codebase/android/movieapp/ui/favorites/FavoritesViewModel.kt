package agency.five.codebase.android.movieapp.ui.favorites

import agency.five.codebase.android.movieapp.data.repository.MovieRepository
import agency.five.codebase.android.movieapp.ui.favorites.mapper.FavoritesMapper
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class FavoritesViewModel(
    private val movieRepository: MovieRepository, private val favoritesMapper: FavoritesMapper
) : ViewModel() {

    private val initialViewState = FavoritesViewState(emptyList())

    private val _favoritesViewState = MutableStateFlow(initialViewState)
    val favoritesViewState = _favoritesViewState.asStateFlow()

    init {
        viewModelScope.launch {
            movieRepository.favoriteMovies().collect(){ movies ->
                _favoritesViewState.value = favoritesMapper.toFavoritesViewState(movies)
            }
        }
    }
}
