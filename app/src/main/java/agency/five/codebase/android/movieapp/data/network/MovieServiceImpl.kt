package agency.five.codebase.android.movieapp.data.network

import agency.five.codebase.android.movieapp.data.network.model.ApiMovieDetails
import agency.five.codebase.android.movieapp.data.network.model.response.MovieCreditsResponse
import agency.five.codebase.android.movieapp.data.network.model.response.MovieResponse
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*

const val BASE_IMAGE_URL = "https://image.tmdb.org/t/p/w500"
private const val BASE_URL = "https://api.themoviedb.org/3"
private const val API_KEY = "fb407104d1e75c75cc7a13bc3d6cffbb"

class MovieServiceImpl(private val client: HttpClient) : MovieService {
    override suspend fun fetchPopularMovies(): MovieResponse =
        client.get {
            url {
                protocol = URLProtocol.HTTPS
                host = BASE_URL
                path("/movie/popular")
                parameter("api_key", API_KEY)
            }
        }

    override suspend fun fetchNowPlayingMovies(): MovieResponse =
        client.get {
            url {
                protocol = URLProtocol.HTTPS
                host = BASE_URL
                path("/movie/now_playing")
                parameter("api_key", API_KEY)
            }
        }

override suspend fun fetchUpcomingMovies(): MovieResponse =
    client.get {
        url {
            protocol = URLProtocol.HTTPS
            host = BASE_URL
            path("/movie/upcoming")
            parameter("api_key", API_KEY)
        }
    }

override suspend fun fetchTopRatedMovies(): MovieResponse =
    client.get {
        url {
            protocol = URLProtocol.HTTPS
            host = BASE_URL
            path("/movie/top_rated")
            parameter("api_key", API_KEY)
        }
    }

override suspend fun fetchMovieDetails(movieId: Int): ApiMovieDetails =
    client.get {
        url {
            protocol = URLProtocol.HTTPS
            host = BASE_URL
            path("/movie/$movieId")
            parameter("api_key", API_KEY)
        }
    }

override suspend fun fetchMovieCredits(movieId: Int): MovieCreditsResponse =
    client.get {
        url {
            protocol = URLProtocol.HTTPS
            host = BASE_URL
            path("/movie/$movieId/credits")
            parameter("api_key", API_KEY)
        }
    }
}
