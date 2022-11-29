package agency.five.codebase.android.movieapp.ui.home.mapper

import agency.five.codebase.android.movieapp.R
import agency.five.codebase.android.movieapp.model.Movie
import agency.five.codebase.android.movieapp.model.MovieCategory
import agency.five.codebase.android.movieapp.ui.component.MovieCategoryLabelTextViewState
import agency.five.codebase.android.movieapp.ui.component.MovieCategoryLabelViewState
import agency.five.codebase.android.movieapp.ui.component.MovieViewState
import agency.five.codebase.android.movieapp.ui.home.HomeMovieCategoryViewState
import agency.five.codebase.android.movieapp.ui.home.HomeMovieViewState

class HomeScreenMapperImpl : HomeScreenMapper {
    override fun toHomeMovieCategoryViewState(
        movieCategories: List<MovieCategory>,
        selectedMovieCategory: MovieCategory,
        movies: List<Movie>
    ): HomeMovieCategoryViewState {
        return HomeMovieCategoryViewState(movieCategories.map { category ->
            MovieCategoryLabelViewState(
                itemId = category.ordinal,
                categoryText = MovieCategoryLabelTextViewState.LabelTextFromResource(
                    getCategoryStringResource(category)
                ),
                isSelected = category == selectedMovieCategory
            )
        }, movies.map { movie ->
            HomeMovieViewState(
                movie.id, MovieViewState(movie.imageUrl, movie.isFavorite)
            )
        })
    }

    private fun getCategoryStringResource(category: MovieCategory): Int {
        return when (category) {
            MovieCategory.POPULAR_STREAMING -> R.string.streaming
            MovieCategory.POPULAR_ON_TV -> R.string.on_tv
            MovieCategory.POPULAR_FOR_RENT -> R.string.for_rent
            MovieCategory.POPULAR_IN_THEATRES -> R.string.in_theatres
            MovieCategory.NOW_PLAYING_MOVIES -> R.string.movies_text
            MovieCategory.NOW_PLAYING_TV -> R.string.tv
            MovieCategory.UPCOMING_TODAY -> R.string.today
            MovieCategory.UPCOMING_THIS_WEEK -> R.string.this_week
        }
    }
}
