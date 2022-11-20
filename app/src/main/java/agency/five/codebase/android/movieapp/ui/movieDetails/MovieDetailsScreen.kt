package agency.five.codebase.android.movieapp.ui.movieDetails

import agency.five.codebase.android.movieapp.R
import agency.five.codebase.android.movieapp.mock.MoviesMock
import agency.five.codebase.android.movieapp.ui.component.*
import agency.five.codebase.android.movieapp.ui.movieDetails.mapper.MovieDetailsMapper
import agency.five.codebase.android.movieapp.ui.movieDetails.mapper.MovieDetailsMapperImpl
import agency.five.codebase.android.movieapp.ui.theme.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

private val movieDetailsMapper: MovieDetailsMapper = MovieDetailsMapperImpl()

private val movieDetailsViewState =
    movieDetailsMapper.toMovieDetailsViewState(MoviesMock.getMovieDetails())

@Composable
fun MovieDetailsRoute() {
    val details by remember {
        mutableStateOf(movieDetailsViewState)
    }
    MovieDetailsScreen(details)
}

@Composable
fun MovieDetailsScreen(
    movieDetailsViewState: MovieDetailsViewState,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .verticalScroll(scrollState)
    ) {
        MovieDetailsHeroSection(movieDetailsViewState = movieDetailsViewState)
        Spacer(
            modifier = Modifier
                .height(
                    20.dp
                )
        )
        MovieDetailsOverviewSection(movieDetailsViewState = movieDetailsViewState)
        Spacer(
            modifier = Modifier
                .height(
                    20.dp
                )
        )
        MovieDetailsCastSection(movieDetailsViewState = movieDetailsViewState)
    }
}

@Composable
fun MovieDetailsHeroSection(
    modifier: Modifier = Modifier
        .fillMaxWidth()
        .height(dimensionResource(id = R.dimen.movie_details_hero_height)),
    movieDetailsViewState: MovieDetailsViewState
) {
    Box(
        modifier = modifier
    ) {
        AsyncImage(
            model = movieDetailsViewState.imageUrl,
            contentDescription = stringResource(id = R.string.movie_image),
            placeholder = painterResource(id = R.drawable.ic_launcher_background),
            error = painterResource(id = R.drawable.ic_launcher_background),
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Bottom
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = dimensionResource(id = R.dimen.movie_details_hero_user_score_padding)
                    ),
                verticalAlignment = Alignment.CenterVertically,

                ) {
                UserScoreProgressBar(
                    userScore = movieDetailsViewState.voteAverage,
                    modifier = Modifier.size(42.dp)
                )
                Text(
                    text = stringResource(id = R.string.user_score_text),
                    style = MovieHeroSupportText,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(start = MaterialTheme.spacing.small)
                )
            }
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
            Text(
                text = movieDetailsViewState.title,
                style = MovieHeroTitle,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(
                    start = dimensionResource(id = R.dimen.movie_details_hero_movie_title_padding)
                )
            )
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
            FavoriteButton(
                modifier = Modifier
                    .padding(
                        start = dimensionResource(id = R.dimen.movie_details_hero_movie_title_padding),
                        bottom = MaterialTheme.spacing.medium
                    ),
                isFavorite = movieDetailsViewState.isFavorite,
                onCLick = {}
            )
        }
    }
}

@Composable
fun MovieDetailsOverviewSection(
    modifier: Modifier = Modifier,
    movieDetailsViewState: MovieDetailsViewState
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                start = MaterialTheme.spacing.medium,
                end = MaterialTheme.spacing.medium
            )
    ) {
        Text(
            text = stringResource(id = R.string.overview_text),
            style = HeaderText,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .fillMaxWidth()
        )
        Spacer(
            modifier = Modifier
                .height(
                    MaterialTheme.spacing.small
                )
        )
        Text(
            text = movieDetailsViewState.overview,
            style = Typography.body1,
            maxLines = 6,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .fillMaxWidth()

        )
        Spacer(
            modifier = Modifier
                .height(
                    MaterialTheme.spacing.medium
                )
        )

        LazyHorizontalGrid(
            rows = GridCells.Fixed(2),
            //verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.large),
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.large),
            modifier = Modifier.height(100.dp)
        ) {
            items(
                items = movieDetailsViewState.crew,
                key = { crewman ->
                    crewman.id
                }
            ) { crewman ->
                CrewItem(
                    viewState = CrewItemViewState(crewman.name, crewman.job),
                    //modifier = Modifier.height(40.dp)
                )
            }
        }

        /*
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium)
        ) {
            items(
                items = movieDetailsViewState.crew,
                key = { crewman ->
                    crewman.id
                }
            ) { crewman ->
                CrewItem(
                    viewState = CrewItemViewState(crewman.name, crewman.job),
                    modifier = Modifier.height(40.dp)
                )
            }
        }
        */

    }
}

@Composable
fun MovieDetailsCastSection(
    modifier: Modifier = Modifier,
    movieDetailsViewState: MovieDetailsViewState
) {
    Column(
        modifier = modifier
            .padding(
                start = MaterialTheme.spacing.medium,
                bottom = MaterialTheme.spacing.medium
            )
    ) {
        Text(
            text = stringResource(id = R.string.top_billed_cast_text),
            style = HeaderText,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)
        ) {
            items(
                items = movieDetailsViewState.cast,
                key = { actor ->
                    actor.id
                }
            ) { actor ->
                ActorCard(
                    actorCardViewState = ActorCardViewState(
                        actor.name,
                        actor.character,
                        actor.imageUrl,
                    ),
                    modifier = Modifier.size(125.dp, 209.dp)
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun MovieDetailsHeroSectionPreview() {
    MovieAppTheme {
        MovieDetailsHeroSection(movieDetailsViewState = movieDetailsViewState)
    }
}

@Preview(showBackground = true)
@Composable
fun MovieDetailsOverviewSectionPreview() {
    MovieAppTheme {
        MovieDetailsOverviewSection(movieDetailsViewState = movieDetailsViewState)
    }
}

@Preview(showBackground = true)
@Composable
fun MovieDetailsCastSectionPreview() {
    MovieAppTheme {
        MovieDetailsCastSection(movieDetailsViewState = movieDetailsViewState)
    }
}

@Preview(showBackground = true)
@Composable
fun MovieDetailsScreenPreview() {
    MovieAppTheme() {
        MovieDetailsScreen(movieDetailsViewState = movieDetailsViewState)
    }
}

