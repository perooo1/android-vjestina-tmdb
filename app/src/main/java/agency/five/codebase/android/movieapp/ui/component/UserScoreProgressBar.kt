package agency.five.codebase.android.movieapp.ui.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
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
    modifier: Modifier = Modifier,
    userScore: Float,                   //0.0 - 10.0
    color: Color = Color.Green,
) {
    Box(
        modifier
            .width(42.dp)
            .height(42.dp),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = modifier.fillMaxSize()) {
            drawArc(
                color = color,
                startAngle = -90f,
                sweepAngle = 360 * (0.1f * userScore),
                useCenter = false,
                style = Stroke(
                    width = 8f,
                    cap = StrokeCap.Round
                )
            )
        }
        Text(text = userScore.toString(), color = Color.Black, fontSize = 15.sp)
    }
}

@Preview(showBackground = true)
@Composable
fun UserScoreProgressBarPreview() {
    UserScoreProgressBar(userScore = 9.0f)
}