package agency.five.codebase.android.movieapp.ui.component

import androidx.annotation.FloatRange
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun UserScoreProgressBar(
    modifier: Modifier = Modifier
        .size(
            width = 42.dp,
            height = 42.dp
        ),
    @FloatRange(from = 0.0, to = 10.0)
    userScore: Float,
    color: Color = Color.Green,
) {
    Box(
        modifier,
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawCircle(
                color = color.copy(alpha = 0.3f),
                style = Stroke(
                    width = 8f,
                    cap = StrokeCap.Round
                )
            )
            drawArc(
                color = color,
                startAngle = UserScoreProgressBarConstants.START_ANGLE,
                sweepAngle = UserScoreProgressBarConstants.CIRCLE_DEGREES * (UserScoreProgressBarConstants.SWEEP_ANGLE_PERCENTAGE_FACTOR * userScore),
                useCenter = false,
                style = Stroke(
                    width = 8f,
                    cap = StrokeCap.Round
                )
            )
        }
        Text(
            text = userScore.toString(),
            color = Color.Black,
            fontSize = 15.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun UserScoreProgressBarPreview() {
    UserScoreProgressBar(userScore = 8.0f)
}

private object UserScoreProgressBarConstants {
    const val CIRCLE_DEGREES = 360
    const val START_ANGLE = -90f
    const val SWEEP_ANGLE_PERCENTAGE_FACTOR = 0.1f
}
