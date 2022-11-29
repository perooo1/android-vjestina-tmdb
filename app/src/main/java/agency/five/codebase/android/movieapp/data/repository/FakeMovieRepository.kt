package agency.five.codebase.android.movieapp.data.repository

import agency.five.codebase.android.movieapp.mock.FavoritesDBMock
import agency.five.codebase.android.movieapp.mock.MoviesMock
import agency.five.codebase.android.movieapp.model.Movie
import agency.five.codebase.android.movieapp.model.MovieCategory
import agency.five.codebase.android.movieapp.model.MovieDetails
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest

class FakeMovieRepository(private val ioDispatcher: CoroutineDispatcher) : MovieRepository {

    private val fakeMovies = MoviesMock.getMoviesList().toMutableList()

    private val movies: Flow<List<Movie>> = FavoritesDBMock.favoriteIds.mapLatest { favoriteIds ->
        fakeMovies.map { movie ->
            movie.copy(isFavorite = favoriteIds.contains(movie.id))
        }
    }.flowOn(ioDispatcher)

    override fun popularMovies(movieCategory: MovieCategory): Flow<List<Movie>> = movies

    override fun nowPlayingMovies(movieCategory: MovieCategory): Flow<List<Movie>> = movies

    override fun upcomingMovies(movieCategory: MovieCategory): Flow<List<Movie>> = movies

    override fun movieDetails(movieId: Int): Flow<MovieDetails> =
        FavoritesDBMock.favoriteIds.mapLatest { favoriteIds ->

            val details = getMovieDetails(movieId)
            val movie = findMatchingMovie(movieId)

            val detailsToEmit = details.copy(
                movie = movie
                    .copy(isFavorite = favoriteIds.contains(movieId))
            )
            detailsToEmit
        }.flowOn(ioDispatcher)

    override fun favoriteMovies(): Flow<List<Movie>> = movies.map {
        it.filter { movie -> movie.isFavorite }
    }

    override suspend fun addMovieToFavorites(movieId: Int) {
        FavoritesDBMock.insert(movieId)
    }

    override suspend fun removeMovieFromFavorites(movieId: Int) {
        FavoritesDBMock.delete(movieId)
    }

    override suspend fun toggleFavorite(movieId: Int) =
        if (FavoritesDBMock.favoriteIds.value.contains(movieId)) {
            removeMovieFromFavorites(movieId)
        } else {
            addMovieToFavorites(movieId)
        }

    private fun getMovieDetails(movieId: Int): MovieDetails {
        val movie = findMatchingMovie(movieId)
        val details = MoviesMock.getMovieDetails()

        return MovieDetails(
            movie = movie,
            voteAverage = details.voteAverage,
            releaseDate = details.releaseDate,
            language = details.language,
            runtime = details.runtime,
            crew = details.crew,
            cast = details.cast
        )
    }

    private fun findMatchingMovie(movieId: Int): Movie = fakeMovies.first { it.id == movieId }
}
