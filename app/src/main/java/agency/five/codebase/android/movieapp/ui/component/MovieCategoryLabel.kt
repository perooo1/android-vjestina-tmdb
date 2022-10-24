package agency.five.codebase.android.movieapp.ui.component

import agency.five.codebase.android.movieapp.R
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class MovieCategoryLabelViewState(
    val itemId: Int,
    val isSelected: Boolean,
    val categoryText: MovieCategoryLabelTextViewState
)

sealed class MovieCategoryLabelTextViewState {
    class LabelTextFromString(val text: String) : MovieCategoryLabelTextViewState()
    class LabelTextFromResource(@StringRes val textRes: Int) : MovieCategoryLabelTextViewState()
}

@Composable
fun MovieCategoryLabel(
    labelViewState: MovieCategoryLabelViewState
) {
    if (labelViewState.isSelected) {
        TextSelected(labelViewState)
    } else {
        TextNotSelected(labelViewState)
    }
}

@Composable
fun TextSelected(labelViewState: MovieCategoryLabelViewState) {
    Column {
        Text(
            text = getTextSource(labelViewState = labelViewState),
            fontSize = 16.sp,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.size(4.dp))
        Divider(thickness = 3.dp, color = Color.Black)
    }
}

@Composable
fun TextNotSelected(labelViewState: MovieCategoryLabelViewState) {
    Text(
        text = getTextSource(labelViewState = labelViewState),
        fontSize = 16.sp,
        color = Color.Gray
    )
}

@Composable
fun getTextSource(labelViewState: MovieCategoryLabelViewState): String {
    val categoryText = labelViewState.categoryText
    return when (categoryText) {
        is MovieCategoryLabelTextViewState.LabelTextFromString ->
            categoryText.text
        is MovieCategoryLabelTextViewState.LabelTextFromResource ->
            stringResource(id = categoryText.textRes)
    }
}


@Preview(showBackground = true)
@Composable
fun MovieCategoryLabelSelectedPreview() {
    val textFromString = MovieCategoryLabelTextViewState.LabelTextFromString("Movies")
    val viewStateString = MovieCategoryLabelViewState(1, true, textFromString)

    MovieCategoryLabel(labelViewState = viewStateString)
}

@Preview(showBackground = true)
@Composable
fun MovieCategoryLabelNotSelectedPreview() {
    val textFromRes = MovieCategoryLabelTextViewState.LabelTextFromResource(R.string.movies_text)
    val viewStateStringRes = MovieCategoryLabelViewState(2, false, textFromRes)

    MovieCategoryLabel(labelViewState = viewStateStringRes)
}