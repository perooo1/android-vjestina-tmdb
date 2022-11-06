package agency.five.codebase.android.movieapp.ui.component

import agency.five.codebase.android.movieapp.ui.theme.Typography
import androidx.annotation.FloatRange
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

const val CIRCLE_DEGREES = 360
const val START_ANGLE = -90f
const val SWEEP_ANGLE_PERCENTAGE_FACTOR = 0.1f

@Composable
fun UserScoreProgressBar(
    modifier: Modifier = Modifier,
    @FloatRange(from = 0.0, to = 10.0)
    userScore: Float,
    color: Color = Color.Green,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawArc(
                color = color.copy(alpha = 0.3f),
                startAngle = START_ANGLE,
                sweepAngle = CIRCLE_DEGREES * 10f,
                useCenter = false,
                style = Stroke(
                    width = 8f,
                    cap = StrokeCap.Round
                )
            )
            drawArc(
                color = color,
                startAngle = START_ANGLE,
                sweepAngle = CIRCLE_DEGREES * (SWEEP_ANGLE_PERCENTAGE_FACTOR * userScore),
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
            style = Typography.h2
        )
    }
}

@Preview(showBackground = true)
@Composable
fun UserScoreProgressBarPreview() {
    UserScoreProgressBar(userScore = 8.0f)
}
