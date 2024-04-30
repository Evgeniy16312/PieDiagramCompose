package com.example.piediagramcompose.content

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.TargetBasedAnimation
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.withFrameNanos
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.example.piediagramcompose.mockData.colorsList
import com.example.piediagramcompose.mockData.populateList
import com.example.piediagramcompose.ui.theme.Background
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun PieChart(
    modifier: Modifier = Modifier,
    selectedMonths: String,
    valueSum: Int,
    radiusOuter: Dp = 110.dp,
    animDuration: Int = 1000,
    parts: List<Float>,
    onPartClick: (Int, Color) -> Unit,
) {
    val strokeWidth = 20.dp
    val strokeWidthClick = 40.dp
    val space = 7.dp
    val textPaint = Paint().asFrameworkPaint().apply {
        isAntiAlias = true
        textSize = 50f
        color = android.graphics.Color.RED
    }

    var selectedPart by remember { mutableStateOf(-1) }

    var colorPart by remember { mutableStateOf(Background) }

    val total = parts.sum()
    val angles = parts.map { it / total * 360 }

    var animationPlayed by remember {
        mutableStateOf(false)
    }

    val animateSize by animateFloatAsState(
        targetValue =
        if (animationPlayed) radiusOuter.value * 2f else 0f,
        animationSpec = tween(
            durationMillis = animDuration,
            delayMillis = 0,
            easing = LinearOutSlowInEasing
        ),
        label = ""
    )

    val animateRotation by animateFloatAsState(
        targetValue = if (animationPlayed) 360f else 0f,
        animationSpec = tween(
            durationMillis = animDuration,
            delayMillis = 0,
            easing = LinearOutSlowInEasing
        ),
        label = ""
    )

    LaunchedEffect(key1 = true) {
        animationPlayed = true
    }
    Box(
        modifier = modifier
            .fillMaxSize()
            .size(animateSize.dp)
            .pointerInput(Unit) {
                detectTapGestures { offset ->
                    val size = this.size
                    val anglePoint = calculateAngle(offset, size)
                    var currentAngle = 0f
                    for ((index, partAngle) in angles.withIndex()) {
                        if (currentAngle + partAngle > anglePoint) {
                            selectedPart = index
                            val selectedColor = colorsList[index]
                            onPartClick(index, selectedColor)
                            colorPart = selectedColor
                            break
                        }
                        currentAngle += partAngle
                    }
                }
            },
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .rotate(animateRotation)
        ) {
            var startAngle = 0f
            parts.indices.forEach { i ->
                val color = colorsList[i]
                val sweepAngle = angles[i]
                drawArc(
                    color = color,
                    startAngle = startAngle,
                    sweepAngle = sweepAngle - space.toPx(),
                    useCenter = false,
                    topLeft = Offset(
                        x = (size.width - size.minDimension) / 2,
                        y = (size.height - size.minDimension) / 2
                    ),
                    size = Size(size.minDimension, size.minDimension),
                    style = Stroke(
                        width = if (i == selectedPart) strokeWidthClick.toPx() else strokeWidth.toPx(),
                        cap = StrokeCap.Round
                    )
                )

                val labelRadius =
                    size.minDimension / 2f // Радиус, на котором должен находиться текст
                val textAngleDegrees = startAngle + sweepAngle / 2 - space.toPx() / 2
                val textAngleRadians = Math.toRadians(textAngleDegrees.toDouble())
                val labelX = center.x + labelRadius * cos(textAngleRadians).toFloat()
                val labelY = center.y + labelRadius * sin(textAngleRadians).toFloat()

                // Отрисовка значения занятой доли в каждой части канваса,
                drawContext.canvas.nativeCanvas.drawText(
                    "${parts[i]}",
                    labelX,
                    labelY,
                    textPaint
                )

                // Отрисовка текста в центре канваса
                drawIntoCanvas {
                    val textPaints = Paint().asFrameworkPaint().apply {
                        isAntiAlias = true
                        textSize = 50f
                    }

                    val textBounds = android.graphics.Rect()
                    textPaints.getTextBounds(selectedMonths, 0, selectedMonths.length, textBounds)
                    it.nativeCanvas.drawText(
                        selectedMonths,
                        size.width / 2f - textBounds.exactCenterX(),
                        size.height / 2f + textBounds.exactCenterY(),
                        textPaints
                    )
                }

                drawIntoCanvas {
                    val textPaints = Paint().asFrameworkPaint().apply {
                        isAntiAlias = true
                        textSize = 50f
                    }

                    val textBounds = android.graphics.Rect()
                    textPaints.getTextBounds(selectedMonths, 0, selectedMonths.length, textBounds)
                    it.nativeCanvas.drawText(
                        (valueSum * 5 + 720).toString() + "$",
                        size.width / 2f - textBounds.exactCenterX(),
                        size.height / 1.6f + textBounds.exactCenterY(),
                        textPaints
                    )
                }

                startAngle += sweepAngle
            }
        }
    }

    SalesListComposable(
        populateList(valueSum), color = colorPart, selected = selectedPart
    )
}

@Composable
fun SalesListComposable(items: List<SalesList>, color: Color, selected: Int) {
    val state by remember { mutableStateOf(false) }
    val anim = remember {
        TargetBasedAnimation(
            animationSpec = tween(durationMillis = 3500),
            typeConverter = Float.VectorConverter,
            initialValue = 0f,
            targetValue = 700f,
        )
    }
    val selectedColor by remember { mutableStateOf(Color.White) }
    var playTime by remember { mutableStateOf(0L) }
    var animationValue by remember { mutableStateOf(0) }

    LaunchedEffect(state) {
        val startTime = withFrameNanos { it }
        do {
            playTime = withFrameNanos { it } - startTime
            animationValue = anim.getValueFromNanos(playTime).toInt()
        } while (!anim.isFinishedFromNanos(playTime))
    }

    LazyColumn(
        modifier = Modifier
            .size(animationValue.dp),
    ) {
        itemsIndexed(items) { index, item ->
            SalesListItem(item = item,
                color = if (index == selected) {
                    color
                } else {
                    selectedColor
                },
                onClick = {
                }
            )
        }
    }
}

fun calculateAngle(offset: Offset, size: IntSize): Double {
    val x = offset.x
    val y = offset.y
    val centerX = size.width / 2
    val centerY = size.height / 2
    val dx = x - centerX
    val dy = y - centerY
    return (atan2(dy.toDouble(), dx.toDouble()) * (180 / Math.PI) + 360) % 360
}

@Composable
fun MyContent(months: String, valueSum: Int) {
    var clickedPart by remember { mutableStateOf("") }
    var selectedColor by remember { mutableStateOf(Background) }

    val parts = remember { listOf(30f, 20f, 10f, 20f, 20f) }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        PieChart(
            modifier = Modifier
                .padding(vertical = 40.dp, horizontal = 24.dp)
                .size(280.dp),
            selectedMonths = months,
            parts = parts,
            valueSum = valueSum
        ) { _, color ->
            clickedPart = months
            selectedColor = color
        }
    }
}