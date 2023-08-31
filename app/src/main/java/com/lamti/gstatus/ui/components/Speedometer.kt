package com.lamti.gstatus.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.DefaultStrokeLineMiter
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lamti.gstatus.ui.theme.GStatusTheme

@Composable
fun Speedometer(modifier: Modifier = Modifier) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(screenHeight / 3)
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 40.dp)
        ) {
            val canvasWidth = size.width
            val canvasHeight = size.height
            val center = Offset(canvasWidth / 2f, canvasHeight / 2f)

            drawArc(
                color = Color.LightGray,
                topLeft = Offset.Zero,
                startAngle = -220f,
                sweepAngle = 262f,
                useCenter = false,
                size = Size(canvasWidth, canvasWidth),
                alpha = 1.0f,
                style = Stroke(
                    width = 33f,
                    miter = DefaultStrokeLineMiter,
                    cap = StrokeCap.Round,
                    join = StrokeJoin.Round,
                    pathEffect = null
                ),
                colorFilter = null,
                blendMode = DrawScope.DefaultBlendMode
            )


        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    GStatusTheme {
        Speedometer()
    }
}
