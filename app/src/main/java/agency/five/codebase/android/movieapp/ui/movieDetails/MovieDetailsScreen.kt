package agency.five.codebase.android.movieapp.ui.movieDetails

import agency.five.codebase.android.movieapp.R
import agency.five.codebase.android.movieapp.ui.component.*
import agency.five.codebase.android.movieapp.ui.theme.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import org.koin.androidx.compose.getViewModel

private const val CREWMEN_GRID_ROWS_COUNT = 2

@Composable
fun MovieDetailsRoute(viewModel: MovieDetailsViewModel) {
    val movieDetailsViewState: MovieDetailsViewState by viewModel.movieDetailsViewState.collectAsState()
    MovieDetailsScreen(movieDetailsViewState, viewModel::toggleFavorite)
}

@Composable
fun MovieDetailsScreen(
    movieDetailsViewState: MovieDetailsViewState,
    onFavoriteButtonClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = modifier.verticalScroll(scrollState)
    ) {
        MovieDetailsHeroSection(movieDetailsViewState, onFavoriteButtonClick)
        Spacer(
            modifier = Modifier.height(
                dimensionResource(id = R.dimen.movie_details_screen_section_spacer)
            )
        )
        MovieDetailsOverviewSection(movieDetailsViewState)
        Spacer(
            modifier = Modifier.height(
                dimensionResource(id = R.dimen.movie_details_screen_section_spacer)
            )
        )
        MovieDetailsCastSection(movieDetailsViewState)
    }
}

@Composable
fun MovieDetailsHeroSection(
    movieDetailsViewState: MovieDetailsViewState,
    onFavoriteButtonClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(dimensionResource(id = R.dimen.movie_details_hero_height))
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
            modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Bottom
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
                    userScore = movieDetailsViewState.voteAverage, modifier = Modifier.size(
                        dimensionResource(id = R.dimen.user_score_progress_bar_size)
                    )
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
            FavoriteButton(modifier = Modifier.padding(
                start = dimensionResource(id = R.dimen.movie_details_hero_movie_title_padding),
                bottom = MaterialTheme.spacing.medium
            ),
                isFavorite = movieDetailsViewState.isFavorite,
                onCLick = { onFavoriteButtonClick(movieDetailsViewState.id) })
        }
    }
}

@Composable
fun MovieDetailsOverviewSection(
    movieDetailsViewState: MovieDetailsViewState, modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = stringResource(id = R.string.overview_text),
            style = HeaderText,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .padding(
                    start = MaterialTheme.spacing.medium, end = MaterialTheme.spacing.medium
                )
                .fillMaxWidth()
        )
        Spacer(
            modifier = Modifier.height(
                MaterialTheme.spacing.small
            )
        )
        Text(
            text = movieDetailsViewState.overview,
            style = Typography.body1,
            modifier = Modifier
                .padding(
                    start = MaterialTheme.spacing.medium, end = MaterialTheme.spacing.medium
                )
                .fillMaxWidth()
        )
        Spacer(
            modifier = Modifier.height(
                MaterialTheme.spacing.medium
            )
        )
        LazyHorizontalGrid(
            rows = GridCells.Fixed(CREWMEN_GRID_ROWS_COUNT),
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .height(
                    dimensionResource(id = R.dimen.movie_details_crewman_grid_height)
                )
                .fillMaxWidth(),
            contentPadding = PaddingValues(
                start = MaterialTheme.spacing.medium, end = MaterialTheme.spacing.medium
            )
        ) {
            items(items = movieDetailsViewState.crew, key = { crewman ->
                crewman.id
            }) { crewman ->
                CrewItem(
                    viewState = CrewItemViewState(
                        crewman.crewItemViewState.name, crewman.crewItemViewState.job
                    )
                )
            }
        }
    }
}

@Composable
fun MovieDetailsCastSection(
    movieDetailsViewState: MovieDetailsViewState, modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = stringResource(id = R.string.top_billed_cast_text),
            style = HeaderText,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .padding(
                    start = MaterialTheme.spacing.medium, bottom = MaterialTheme.spacing.medium
                )
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small),
            contentPadding = PaddingValues(
                start = MaterialTheme.spacing.medium, end = MaterialTheme.spacing.medium
            )
        ) {
            items(items = movieDetailsViewState.cast, key = { actor ->
                actor.id
            }) { actor ->
                ActorCard(
                    actorCardViewState = ActorCardViewState(
                        actor.actorCardViewState.name,
                        actor.actorCardViewState.character,
                        actor.actorCardViewState.imageUrl
                    ), modifier = Modifier.size(
                        dimensionResource(id = R.dimen.actor_card_width),
                        dimensionResource(id = R.dimen.actor_card_height)
                    )
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MovieDetailsScreenPreview() {
    MovieAppTheme() {
        MovieDetailsScreen(movieDetailsViewState = getViewModel(), {})
    }
}
