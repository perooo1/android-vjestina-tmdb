package agency.five.codebase.android.movieapp.ui.movieDetails.di

import agency.five.codebase.android.movieapp.ui.movieDetails.MovieDetailsViewModel
import agency.five.codebase.android.movieapp.ui.movieDetails.mapper.MovieDetailsMapper
import agency.five.codebase.android.movieapp.ui.movieDetails.mapper.MovieDetailsMapperImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val movieDetailsModule = module {
    viewModel { parameters ->
        MovieDetailsViewModel(
            movieId = parameters.get(),
            movieRepository = get(),
            movieDetailsMapper = get()
        )
    }
    single<MovieDetailsMapper> { MovieDetailsMapperImpl() }
}
