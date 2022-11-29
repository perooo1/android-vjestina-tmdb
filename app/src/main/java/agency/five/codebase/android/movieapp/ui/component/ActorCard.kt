package agency.five.codebase.android.movieapp.ui.component

import agency.five.codebase.android.movieapp.R
import agency.five.codebase.android.movieapp.mock.MoviesMock
import agency.five.codebase.android.movieapp.ui.theme.Typography
import agency.five.codebase.android.movieapp.ui.theme.spacing
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage

data class ActorCardViewState(
    val name: String,
    val character: String,
    val imageUrl: String?
)

@Composable
fun ActorCard(
    actorCardViewState: ActorCardViewState,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(dimensionResource(id = R.dimen.card_corner_radius)),
        elevation = dimensionResource(id = R.dimen.card_elevation)
    ) {
        Column(
            Modifier.fillMaxSize()
        ) {
            AsyncImage(
                model = actorCardViewState.imageUrl,
                placeholder = painterResource(id = R.drawable.ic_launcher_background),
                error = painterResource(id = R.drawable.ic_launcher_background),
                contentDescription = stringResource(R.string.actor_image),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(
                        dimensionResource(id = R.dimen.cast_member_image_height)
                    )
            )
            Text(
                text = actorCardViewState.name,
                style = Typography.h3,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .padding(
                        start = MaterialTheme.spacing.actor_card_text_padding_start_end,
                        top = MaterialTheme.spacing.actor_card_text_padding_top,
                        end = MaterialTheme.spacing.actor_card_text_padding_start_end
                    )
            )
            Text(
                text = actorCardViewState.character,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = Typography.h4,
                modifier = Modifier
                    .padding(
                        start = MaterialTheme.spacing.actor_card_text_padding_start_end,
                        end = MaterialTheme.spacing.actor_card_text_padding_start_end
                    )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ActorCardPreview() {
    val actor = MoviesMock.getActor()
    val viewState = ActorCardViewState(
        actor.name,
        actor.character,
        actor.imageUrl
    )

    ActorCard(actorCardViewState = viewState)
}
