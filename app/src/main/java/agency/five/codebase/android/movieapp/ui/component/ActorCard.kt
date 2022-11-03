package agency.five.codebase.android.movieapp.ui.component

import agency.five.codebase.android.movieapp.R
import agency.five.codebase.android.movieapp.mock.MoviesMock
import agency.five.codebase.android.movieapp.ui.theme.LocalSpacing
import agency.five.codebase.android.movieapp.ui.theme.Typography
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
        .size(
            width = 125.dp,
            height = 209.dp
        )
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(
            topStart = dimensionResource(id = R.dimen.card_corner_radius),
            topEnd = dimensionResource(id = R.dimen.card_corner_radius)
        ),
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
                maxLines = ActorCardConstants.MAX_LINES,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .padding(
                        start = LocalSpacing.current.actor_card_text_padding_start_end,
                        top = LocalSpacing.current.actor_card_text_padding_top,
                        end = LocalSpacing.current.actor_card_text_padding_start_end
                    )
            )
            Text(
                text = actorCardViewState.character,
                maxLines = ActorCardConstants.MAX_LINES,
                overflow = TextOverflow.Ellipsis,
                style = Typography.h4,
                modifier = Modifier
                    .padding(
                        start = LocalSpacing.current.actor_card_text_padding_start_end,
                        end = LocalSpacing.current.actor_card_text_padding_start_end
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

private object ActorCardConstants {
    const val MAX_LINES = 1
}
