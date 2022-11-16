package agency.five.codebase.android.movieapp.ui.component

import agency.five.codebase.android.movieapp.mock.MoviesMock
import agency.five.codebase.android.movieapp.ui.theme.Typography
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview

data class CrewItemViewState(
    val name: String,
    val job: String
)

@Composable
fun CrewItem(
    viewState: CrewItemViewState,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = viewState.name,
            style = Typography.h2,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
        Text(
            text = viewState.job,
            style = Typography.subtitle1,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CrewItemPreview() {
    val crewman = MoviesMock.getCrewman()
    val crewItemViewState = CrewItemViewState(crewman.name, crewman.job)

    CrewItem(viewState = crewItemViewState)
}
