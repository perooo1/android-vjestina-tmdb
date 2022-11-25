package agency.five.codebase.android.movieapp.ui.movieDetails.mapper

import agency.five.codebase.android.movieapp.model.MovieDetails
import agency.five.codebase.android.movieapp.ui.component.ActorCardViewState
import agency.five.codebase.android.movieapp.ui.component.CrewItemViewState
import agency.five.codebase.android.movieapp.ui.movieDetails.ActorViewState
import agency.five.codebase.android.movieapp.ui.movieDetails.CrewmanViewState
import agency.five.codebase.android.movieapp.ui.movieDetails.MovieDetailsViewState

class MovieDetailsMapperImpl : MovieDetailsMapper {
    override fun toMovieDetailsViewState(movieDetails: MovieDetails): MovieDetailsViewState {
        return MovieDetailsViewState(
            id = movieDetails.movie.id,
            imageUrl = movieDetails.movie.imageUrl,
            voteAverage = movieDetails.voteAverage,
            title = movieDetails.movie.title,
            overview = movieDetails.movie.overview,
            isFavorite = movieDetails.movie.isFavorite,
            crew = movieDetails.crew.map { crewman ->
                CrewmanViewState(
                    crewman.id,
                    CrewItemViewState(crewman.name, crewman.job)
                )
            },
            cast = movieDetails.cast.map { actor ->
                ActorViewState(
                    actor.id,
                    ActorCardViewState(actor.name, actor.character, actor.imageUrl)
                )
            }
        )
    }
}
