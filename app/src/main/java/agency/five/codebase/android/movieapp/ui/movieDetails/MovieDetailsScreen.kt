package agency.five.codebase.android.movieapp.ui.movieDetails

import agency.five.codebase.android.movieapp.mock.MoviesMock
import agency.five.codebase.android.movieapp.ui.movieDetails.mapper.MovieDetailsMapper
import agency.five.codebase.android.movieapp.ui.movieDetails.mapper.MovieDetailsMapperImpl
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

private val movieDetailsMapper: MovieDetailsMapper = MovieDetailsMapperImpl()

private val crewmanViewState = movieDetailsMapper.toCrewmanViewState(MoviesMock.getCrewman())
private val actorViewState = movieDetailsMapper.toActorViewState(MoviesMock.getActor())
private val movieDetailsViewState =
    movieDetailsMapper.toMovieDetailsViewState(MoviesMock.getMovieDetails())

@Composable
fun MovieDetailsRoute() {
    //TODO
}

@Composable
fun MovieDetailsScreen(
    modifier: Modifier = Modifier,
    movieDetailsViewState: MovieDetailsViewState
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .verticalScroll(scrollState)
    ) {
        MovieDetailsHeroSection()
        MovieDetailsOverviewSection()
        MovieDetailsCastSection()
    }
}

@Composable
fun MovieDetailsHeroSection() {
    //TODO
}

@Composable
fun MovieDetailsOverviewSection() {
    //TODO
}

@Composable
fun MovieDetailsCastSection() {
    //TODO
}


@Preview(showBackground = true)
@Composable
fun MovieDetailsHeroSectionPreview() {

}


@Preview(showBackground = true)
@Composable
fun MovieDetailsOverviewSectionPreview() {

}

@Preview(showBackground = true)
@Composable
fun MovieDetailsCastSectionPreview() {

}

@Preview(showBackground = true)
@Composable
fun MovieDetailsScreenPreview() {

}

