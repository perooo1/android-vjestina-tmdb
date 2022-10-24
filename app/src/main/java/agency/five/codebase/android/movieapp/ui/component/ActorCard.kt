package agency.five.codebase.android.movieapp.ui.component

import agency.five.codebase.android.movieapp.R
import agency.five.codebase.android.movieapp.mock.MoviesMock
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
        modifier = modifier
            .width(125.dp)
            .height(209.dp),
        shape = RoundedCornerShape(
            topStart = 10.dp,
            topEnd = 10.dp
        ),
        elevation = 5.dp
    ) {
        Column(
            modifier.fillMaxSize()
        ) {
            AsyncImage(
                model = actorCardViewState.imageUrl,
                placeholder = painterResource(id = R.drawable.ic_launcher_background),
                contentDescription = stringResource(R.string.actor_image),
                contentScale = ContentScale.Crop,
                modifier = Modifier.height(170.dp)
            )
            Text(
                text = actorCardViewState.name,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(start = 10.dp, top = 6.dp, end = 28.dp)
            )
            Text(
                text = actorCardViewState.character,
                fontSize = 8.sp,
                style = TextStyle(
                    color = Color.LightGray
                ),
                modifier = Modifier
                    .padding(start = 10.dp, end = 7.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ActorCardPreview() {
    val actor = MoviesMock.getActor()
    val viewState = ActorCardViewState(actor.name, actor.character, actor.imageUrl)

    ActorCard(actorCardViewState = viewState)
}