package agency.five.codebase.android.movieapp

import agency.five.codebase.android.movieapp.data.di.dataModule
import agency.five.codebase.android.movieapp.ui.favorites.di.favoritesModule
import android.app.Application
import android.util.Log
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MovieApp : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin{
            androidLogger(Level.INFO)
            androidContext(this@MovieApp)
            modules(dataModule, favoritesModule)
        }

        Log.d("MovieApp", "App started")
    }
}
