package agency.five.codebase.android.movieapp.ui.favorites

import agency.five.codebase.android.movieapp.ui.component.MovieViewState

data class FavoritesMovieViewState(
    val id: Int,
    val movieViewState: MovieViewState
)

data class FavoritesViewState(
    val favoriteMovies: List<FavoritesMovieViewState>
)
