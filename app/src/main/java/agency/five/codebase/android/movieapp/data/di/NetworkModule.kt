package agency.five.codebase.android.movieapp.data.di

import agency.five.codebase.android.movieapp.data.network.MovieService
import agency.five.codebase.android.movieapp.data.network.MovieServiceImpl
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.logging.*
import org.koin.dsl.module

val networkModule = module {
    single<MovieService> { MovieServiceImpl(client = get()) }

    single {
        HttpClient(Android) {
            expectSuccess = true
            engine {
                connectTimeout = 100_000
                socketTimeout = 100_000
            }
            install(Logging){
                logger = Logger.DEFAULT
                level = LogLevel.ALL
            }
        }
    }

}
