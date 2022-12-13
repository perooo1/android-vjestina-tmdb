package agency.five.codebase.android.movieapp.data.network.model

import agency.five.codebase.android.movieapp.model.Actor
import agency.five.codebase.android.movieapp.model.Crewman
import agency.five.codebase.android.movieapp.model.Movie
import agency.five.codebase.android.movieapp.model.MovieDetails
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApiMovieDetails(
    val movie: ApiMovie,
    @SerialName("vote_average")
    val voteAverage: Float,             //double, also in ApiMovie  todo
    @SerialName("release_date")
    val releaseDate: String,            //double, also in ApiMovie  todo
    @SerialName("original_language")
    val language: String,
    @SerialName("runtime")
    val runtime: Int?,
) {
    fun toMovieDetails(isFavorite: Boolean, crew: List<Crewman>, cast: List<Actor>) = MovieDetails(
        movie = movie.toMovie(isFavorite),
        voteAverage = voteAverage,
        releaseDate = releaseDate,
        language = language,
        runtime = runtime ?: 0,
        crew = crew,
        cast = cast
    )
}
