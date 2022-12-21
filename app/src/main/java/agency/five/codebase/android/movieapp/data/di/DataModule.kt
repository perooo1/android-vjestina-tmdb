package agency.five.codebase.android.movieapp.data.di

import agency.five.codebase.android.movieapp.data.database.MovieAppDatabase
import agency.five.codebase.android.movieapp.data.repository.MovieRepository
import agency.five.codebase.android.movieapp.data.repository.MovieRepositoryImpl
import androidx.room.Room
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

private const val APP_DATABASE_NAME = "app_database.db"

val dataModule = module {
    single<MovieRepository>{
        MovieRepositoryImpl(get(),get(),Dispatchers.IO)
    }
}

val databaseModule = module {
    single{
        Room.databaseBuilder(
            androidApplication(),
            MovieAppDatabase::class.java,
            APP_DATABASE_NAME
        ).build()
    }
    single{
        get<MovieAppDatabase>().getMovieDao()
    }
}


