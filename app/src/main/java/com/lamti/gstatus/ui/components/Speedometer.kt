package com.lamti.gstatus.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.PaintingStyle
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.toArgb
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
import com.lamti.gstatus.ui.converters.toArcValue
import com.lamti.gstatus.ui.converters.toIndicatorValue
import com.lamti.gstatus.ui.theme.GStatusTheme
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

private val values = listOf(0, 5, 10, 50, 100, 250, 500, 750, 1000)

@OptIn(ExperimentalTextApi::class)
@Composable
fun Speedometer(modifier: Modifier = Modifier, value: Float = 0f) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    var indicatorValue by remember { mutableStateOf(value.toIndicatorValue()) }
    var arcValue by remember { mutableStateOf(value.toArcValue()) }

    val mainArcAnimation = remember {
        Animatable(0f)
    }

    LaunchedEffect(Unit) {
        mainArcAnimation.animateTo(
            targetValue = 238f,
            animationSpec = tween(
                durationMillis = 350,
                delayMillis = 150,
                easing = FastOutSlowInEasing
            ),
        )
    }

    val textLinesAnimation = remember {
        List(values.size) {
            Animatable(0f)
        }
    }

    LaunchedEffect(Unit) {
        var delay = 0
        textLinesAnimation.forEach {
            it.animateTo(
                targetValue = 1f,
                animationSpec = tween(
                    durationMillis = 20,
                    delayMillis = delay,
                    easing = FastOutLinearInEasing
                ),
            )
            delay += 3
        }
    }

    LaunchedEffect(key1 = value) {
        indicatorValue = value.toIndicatorValue()
        arcValue = value.toArcValue()
    }

    val indicatorAnimation = remember {
        Animatable(0f)
    }

    LaunchedEffect(Unit) {
        indicatorAnimation.animateTo(
            targetValue = 1f,
            animationSpec = tween(
                durationMillis = 220,
                delayMillis = 750,
                easing = FastOutSlowInEasing
            )
        )
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(screenWidth)
            .padding(40.dp)
    ) {
        val textMeasurer = rememberTextMeasurer()

        // Paint for the shadow
        val paint = remember {
            Paint().apply {
                style = PaintingStyle.Stroke
                strokeWidth = 50f
            }
        }
        val frameworkPaint = remember { paint.asFrameworkPaint() }
        val color = Color.Blue
        val transparent = color
            .copy(alpha = 0f)
            .toArgb()

        frameworkPaint.color = transparent
        frameworkPaint.setShadowLayer(
            50f,
            0f,
            0f,
            color
                .copy(alpha = .5f)
                .toArgb()
        )

        Canvas(
            modifier = Modifier
                .fillMaxSize()
        ) {
            val canvasWidth = size.width
            val canvasHeight = size.height
            val center = Offset(canvasWidth / 2f, canvasHeight / 2f)
            var textValuesIndex = 0

            // Draw main arc
            drawArc(
                color = Color.LightGray,
                topLeft = Offset.Zero,
                startAngle = -208f,
                sweepAngle = mainArcAnimation.value,
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
                    val measuredTextCenterX = measuredText.size.width / 2 - 80
                    val measuredTextCenterY = measuredText.size.height / 2

                    val textPointCenter = Offset(
                        x = (center.x + measuredTextCenterX) +
                                (radius - lineSpace - lineHeight - lineHeight / 2 + measuredTextCenterX) * cos(angle),
                        y = (center.y - measuredTextCenterY) +
                                (radius - lineSpace - lineHeight - lineHeight / 2 - measuredTextCenterY) * sin(angle)
                    )

                    scale(scale = textLinesAnimation[textValuesIndex].value) {
                        drawText(
                            textLayoutResult = measuredText,
                            topLeft = textPointCenter
                        )
                    }

                    textValuesIndex++
                }
            }

            // Draw indicator
            scale(scale = indicatorAnimation.value) {
                drawCircle(
                    center = center,
                    radius = 40f,
                    color = Color.LightGray,
                )
                val middleTopLeft = Offset(
                    x = center.x - 3f,
                    y = center.y - 230f
                )
                val middleTopRight = Offset(
                    x = center.x + 3f,
                    y = center.y - 230f
                )
                val bottomLeft = Offset(
                    x = center.x - 20f,
                    y = center.y
                )
                val bottomRight = Offset(
                    x = center.x + 20f,
                    y = center.y
                )
                val indicatorPath = Path().apply {
                    moveTo(middleTopLeft.x, middleTopLeft.y)
                    lineTo(bottomLeft.x, bottomLeft.y)
                    lineTo(bottomRight.x, bottomRight.y)
                    lineTo(middleTopRight.x, middleTopRight.y)
                }
                rotate(
                    degrees = indicatorValue,
                    pivot = center
                ) {
                    drawPath(
                        path = indicatorPath,
                        color = Color.Blue,
                    )
                }
                drawCircle(
                    center = center,
                    radius = 20f,
                    color = Color.Blue,
                )
                drawCircle(
                    center = center,
                    radius = 8f,
                    color = Color.White,
                )
            }

            // Draw value arc shadow-glow
            this.drawIntoCanvas {
                it.drawArc(
                    0f,
                    0f,
                    canvasWidth,
                    canvasWidth,
                    -208f,
                    arcValue,
                    false,
                    paint
                )
            }

            // Draw value arc
            drawArc(
                color = Color.Blue,
                topLeft = Offset.Zero,
                startAngle = -208f,
                sweepAngle = arcValue,
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
