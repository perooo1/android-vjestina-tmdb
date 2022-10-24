package agency.five.codebase.android.movieapp.ui.component

import agency.five.codebase.android.movieapp.R
import agency.five.codebase.android.movieapp.mock.MoviesMock
import agency.five.codebase.android.movieapp.model.Movie
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun MovieCard(
    movie: Movie,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .width(122.dp)
            .height(179.dp)
            .clickable { },
        shape = RoundedCornerShape(10.dp),
        elevation = 5.dp
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.TopStart
        ) {
            AsyncImage(
                model = movie.imageUrl,
                placeholder = painterResource(id = R.drawable.ic_launcher_background),
                contentDescription = stringResource(R.string.actor_image),                  //should be movie img, having issues with Android studio atm
                contentScale = ContentScale.Crop,
                modifier = Modifier.height(170.dp)
            )
            FavoriteButton()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MovieCardPreview() {
    val movie = MoviesMock.getMovieDetails().movie
    MovieCard(movie = movie)
}