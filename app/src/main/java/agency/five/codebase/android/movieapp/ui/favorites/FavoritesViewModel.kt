package agency.five.codebase.android.movieapp.ui.favorites

import agency.five.codebase.android.movieapp.data.repository.MovieRepository
import agency.five.codebase.android.movieapp.ui.favorites.mapper.FavoritesMapper
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class FavoritesViewModel(
    private val movieRepository: MovieRepository, private val favoritesMapper: FavoritesMapper
) : ViewModel() {

    private val favoriteMovies = movieRepository.favoriteMovies()

    val favoritesViewState: StateFlow<FavoritesViewState> = favoriteMovies.map { movies ->
        favoritesMapper.toFavoritesViewState(movies)

    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        FavoritesViewState(emptyList())
    )


}
