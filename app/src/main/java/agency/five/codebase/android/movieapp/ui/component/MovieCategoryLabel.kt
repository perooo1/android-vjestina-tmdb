package agency.five.codebase.android.movieapp.ui.component

import agency.five.codebase.android.movieapp.R
import agency.five.codebase.android.movieapp.ui.theme.LocalSpacing
import agency.five.codebase.android.movieapp.ui.theme.Typography
import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview

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
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    labelViewState: MovieCategoryLabelViewState
) {
    Column(
        modifier = modifier
            .clickable { onClick },
    ) {
        if (labelViewState.isSelected) {
            TextSelected(labelViewState)
        } else {
            TextNotSelected(labelViewState)
        }
    }
}

@Composable
fun TextSelected(labelViewState: MovieCategoryLabelViewState) {
    Text(
        text = getTextSource(labelViewState = labelViewState),
        style = Typography.body1,
        modifier = Modifier
            .fillMaxWidth()
            .height(
                dimensionResource(id = R.dimen.movie_category_label_selected_text_height)
            )
    )
    Spacer(modifier = Modifier.size(LocalSpacing.current.extraSmall))
    Divider(
        thickness = dimensionResource(id = R.dimen.movie_category_divider_thickness),
        color = Color.Black
    )

}

@Composable
fun TextNotSelected(labelViewState: MovieCategoryLabelViewState) {
    Text(
        text = getTextSource(labelViewState = labelViewState),
        style = Typography.body1,
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

    MovieCategoryLabel(labelViewState = viewStateString, onClick = {})
}

@Preview(showBackground = true)
@Composable
fun MovieCategoryLabelNotSelectedPreview() {
    val textFromRes = MovieCategoryLabelTextViewState.LabelTextFromResource(R.string.movies_text)
    val viewStateStringRes = MovieCategoryLabelViewState(2, false, textFromRes)

    MovieCategoryLabel(labelViewState = viewStateStringRes, onClick = {})
}
