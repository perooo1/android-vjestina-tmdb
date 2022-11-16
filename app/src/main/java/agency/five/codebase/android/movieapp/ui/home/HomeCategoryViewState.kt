package agency.five.codebase.android.movieapp.ui.home

import agency.five.codebase.android.movieapp.ui.component.MovieCategoryLabelViewState

data class HomeMovieViewState(
    val id: Int,
    val imageUrl: String?,
    val isFavorite: Boolean,
)

data class HomeMovieCategoryViewState(
    val movieCategories: List<MovieCategoryLabelViewState>,
    val movies: List<HomeMovieViewState>
)
