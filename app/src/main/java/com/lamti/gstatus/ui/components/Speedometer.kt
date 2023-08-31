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
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lamti.gstatus.ui.theme.GStatusTheme
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

private val values = listOf(0, 5, 10, 50, 100, 250, 500, 750, 1000)

@OptIn(ExperimentalTextApi::class)
@Composable
fun Speedometer(modifier: Modifier = Modifier) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(screenWidth)
            .padding(40.dp)
    ) {
        val textMeasurer = rememberTextMeasurer()

        Canvas(
            modifier = Modifier
                .fillMaxSize()
        ) {
            val canvasWidth = size.width
            val canvasHeight = size.height
            val center = Offset(canvasWidth / 2f, canvasHeight / 2f)
            var textValuesIndex = 0

            drawArc(
                color = Color.LightGray,
                topLeft = Offset.Zero,
                startAngle = -208f,
                sweepAngle = 238f,
                useCenter = false,
                size = Size(canvasWidth, canvasWidth),
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

            // Draw lines
            for (i in -208..30) {
                val angle = i.toFloat() * (PI / 180).toFloat()
                val radius = canvasWidth / 2
                val lineSpace = 60f

                val lineHeight = when {
                    (i % 6 == 0 && i % 5 == 0) || i == -208 -> 60f
                    (i % 6 == 0 && i != -204) || i == -203 -> 30f
                    else -> 0f
                }
                val startPoint = Offset(
                    x = center.x + (radius - lineSpace) * cos(angle),
                    y = center.y + (radius - lineSpace) * sin(angle)
                )
                val endPoint = Offset(
                    x = center.x + (radius - lineSpace - lineHeight) * cos(angle),
                    y = center.y + (radius - lineSpace - lineHeight) * sin(angle)
                )

                drawLine(
                    start = startPoint,
                    end = endPoint,
                    strokeWidth = 5f,
                    color = Color.LightGray
                )

                // Draw text
                if (lineHeight == 60f) {
                    val measuredText = textMeasurer.measure(
                        text = AnnotatedString(text = values[textValuesIndex].toString()),
                        style = TextStyle(fontSize = 18.sp, textAlign = TextAlign.Center, color = Color.LightGray)
                    )
                    val measuredTextCenterX = measuredText.size.width / 2 - 75
                    val measuredTextCenterY = measuredText.size.height / 2

                    val textPointCenter = Offset(
                        x = (center.x + measuredTextCenterX) +
                                (radius - lineSpace - lineHeight - lineHeight / 2 + measuredTextCenterX) * cos(angle),
                        y = (center.y - measuredTextCenterY) +
                                (radius - lineSpace - lineHeight - lineHeight / 2 - measuredTextCenterY) * sin(angle)
                    )

                    drawText(
                        textLayoutResult = measuredText,
                        topLeft = textPointCenter
                    )

                    textValuesIndex++
                }
            }
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
