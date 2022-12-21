package agency.five.codebase.android.movieapp

import agency.five.codebase.android.movieapp.data.di.dataModule
import agency.five.codebase.android.movieapp.data.di.databaseModule
import agency.five.codebase.android.movieapp.data.di.networkModule
import agency.five.codebase.android.movieapp.ui.favorites.di.favoritesModule
import agency.five.codebase.android.movieapp.ui.home.di.homeScreenModule
import agency.five.codebase.android.movieapp.ui.movieDetails.di.movieDetailsModule
import android.app.Application
import android.util.Log
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MovieApp : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.INFO)
            androidContext(this@MovieApp)
            modules(
                dataModule,
                favoritesModule,
                movieDetailsModule,
                homeScreenModule,
                networkModule,
                databaseModule
            )
        }

        Log.d("MovieApp", "App started")
    }
}
