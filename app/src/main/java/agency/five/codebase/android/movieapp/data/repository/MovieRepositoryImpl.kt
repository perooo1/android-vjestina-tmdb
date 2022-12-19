package agency.five.codebase.android.movieapp.data.repository

import agency.five.codebase.android.movieapp.data.database.DbFavoriteMovie
import agency.five.codebase.android.movieapp.data.database.FavoriteMovieDao
import agency.five.codebase.android.movieapp.data.network.MovieService
import agency.five.codebase.android.movieapp.model.Movie
import agency.five.codebase.android.movieapp.model.MovieCategory
import agency.five.codebase.android.movieapp.model.MovieDetails
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*

private const val STOP_TIMEOUT_MILIS = 1000L

class MovieRepositoryImpl(
    private val movieService: MovieService,
    private val movieDao: FavoriteMovieDao,
    private val bgDispatcher: CoroutineDispatcher
) : MovieRepository {

    private val moviesByCategory: Map<MovieCategory, Flow<List<Movie>>> =
        MovieCategory.values().associateWith { category ->
            flow {
                val movieResponse = when (category) {
                    MovieCategory.POPULAR_STREAMING -> movieService.fetchPopularMovies()
                    MovieCategory.POPULAR_ON_TV -> movieService.fetchUpcomingMovies()
                    MovieCategory.POPULAR_FOR_RENT -> movieService.fetchNowPlayingMovies()
                    MovieCategory.POPULAR_IN_THEATRES -> movieService.fetchTopRatedMovies()
                    MovieCategory.NOW_PLAYING_MOVIES -> movieService.fetchNowPlayingMovies()
                    MovieCategory.NOW_PLAYING_TV -> movieService.fetchUpcomingMovies()
                    MovieCategory.UPCOMING_TODAY -> movieService.fetchUpcomingMovies()
                    MovieCategory.UPCOMING_THIS_WEEK -> movieService.fetchTopRatedMovies()
                }
                emit(movieResponse.movies)
            }.flatMapLatest { apiMovies ->
                movieDao.favorites().map { favoriteMovies ->
                    apiMovies.map { apiMovie ->
                        apiMovie.toMovie(isFavorite = favoriteMovies.any { it.id == apiMovie.id })
                    }
                }
            }.shareIn(
                scope = CoroutineScope(bgDispatcher),
                started = SharingStarted.WhileSubscribed(STOP_TIMEOUT_MILIS),
                replay = 1
            )
        }

    private val favorites = movieDao.favorites().map { dbFavoriteMovies ->
        dbFavoriteMovies.map { favorite ->
            Movie(
                id = favorite.id,
                imageUrl = favorite.posterUrl,
                title = "",
                overview = "",
                isFavorite = true
            )
        }
    }.shareIn(
        scope = CoroutineScope(bgDispatcher),
        started = SharingStarted.WhileSubscribed(STOP_TIMEOUT_MILIS),
        replay = 1
    )

    override fun movies(movieCategory: MovieCategory): Flow<List<Movie>> =
        moviesByCategory[movieCategory]!!

    override fun movieDetails(movieId: Int): Flow<MovieDetails> = flow {
        emit(movieService.fetchMovieDetails(movieId) to movieService.fetchMovieCredits(movieId))
    }.flatMapLatest { (apiMovieDetails, apiMovieCredits) ->
        movieDao.favorites().map { favoriteMovies ->
            apiMovieDetails.toMovieDetails(
                isFavorite = favoriteMovies.any { it.id == apiMovieDetails.id },
                crew = apiMovieCredits.crew.map { it.toCrewman() },
                cast = apiMovieCredits.cast.map { it.toActor() }
            )
        }
    }.flowOn(bgDispatcher)

    override fun favoriteMovies(): Flow<List<Movie>> = favorites

    override suspend fun addMovieToFavorites(movieId: Int) {
        val movie = findMovie(movieId)
        movieDao.insertMovie(DbFavoriteMovie(movie.id, movie.imageUrl ?: ""))
    }

    private suspend fun findMovie(movieId: Int): Movie {
        lateinit var movie: Movie
        moviesByCategory.values.forEach { value ->
            val movies = value.first()
            movies.forEach {
                if (it.id == movieId) {
                    movie = it
                }
            }
        }
        return movie
    }

    override suspend fun removeMovieFromFavorites(movieId: Int) = movieDao.deleteMovie(movieId)

    override suspend fun toggleFavorite(movieId: Int) {
        val favoriteMovies = favorites.first()
        val favoriteMovie = favoriteMovies.filter {
            it.id == movieId
        }
        if (favoriteMovie.isNotEmpty()) {
            removeMovieFromFavorites(movieId)
        } else {
            addMovieToFavorites(movieId)
        }
    }
}
