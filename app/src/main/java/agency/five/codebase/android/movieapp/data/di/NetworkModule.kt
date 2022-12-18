package agency.five.codebase.android.movieapp.data.di

import agency.five.codebase.android.movieapp.data.network.MovieService
import agency.five.codebase.android.movieapp.data.network.MovieServiceImpl
import android.util.Log
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import org.koin.dsl.module

private const val CONNECTION_TIMEOUT_MILIS = 100_000
private const val SOCKET_TIMEOUT_MILIS = 100_000

val networkModule = module {
    single<MovieService> { MovieServiceImpl(client = get()) }

    single {
        HttpClient(Android) {
            expectSuccess = true
            engine {
                connectTimeout = CONNECTION_TIMEOUT_MILIS
                socketTimeout = SOCKET_TIMEOUT_MILIS
            }
            install(ContentNegotiation) {
                json(Json {
                    isLenient = true
                    ignoreUnknownKeys = true
                })
            }
            install(Logging) {
                level = LogLevel.BODY
                logger = object : Logger {
                    override fun log(message: String) {
                        Log.d("Ktor: ", message)
                    }
                }
            }
        }
    }
}
