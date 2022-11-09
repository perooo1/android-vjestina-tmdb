package agency.five.codebase.android.movieapp.ui.home

import agency.five.codebase.android.movieapp.mock.MoviesMock
import agency.five.codebase.android.movieapp.model.MovieCategory
import agency.five.codebase.android.movieapp.ui.home.mapper.HomeScreenMapper
import agency.five.codebase.android.movieapp.ui.home.mapper.HomeScreenMapperImpl
import androidx.compose.runtime.Composable

val homeMapper: HomeScreenMapper = HomeScreenMapperImpl()

val movies = MoviesMock.getMoviesList()

//TODO popularCategoryViewState / nowPlayingCategoryViewState / upcomingCategoryViewState

@Composable
fun HomeScreenRoute(){
    //TODO
}

@Composable
fun HomeScreen(){

}
