package com.example.piediagramcompose.content

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.example.piediagramcompose.ui.theme.Background
import kotlinx.coroutines.launch

data class ArcData(
    val animation: Animatable<Float, AnimationVector1D>,
    val targetSweepAngle: Float,
    val startAngle: Float,
    val color: Color,
)

@Composable
fun AnimatedGapPieChart(
    modifier: Modifier = Modifier,
    pieDataPoints: List<PieData>,
) {
    val gapDegrees = 15f
    val numberOfGaps = pieDataPoints.size
    val remainingDegrees = 360f - (gapDegrees * numberOfGaps)
    val localModifier = modifier
        .fillMaxWidth()
        .height(330.dp)
        .background(Background)
    val total = pieDataPoints.fold(0f)
    { acc, pieData -> acc + pieData.value }.div(remainingDegrees)
    var currentSum = 0f
    val arcs = pieDataPoints.mapIndexed { index, it ->
        val startAngle = currentSum + (index * gapDegrees)
        currentSum += it.value / total
        ArcData(
            targetSweepAngle = it.value / total,
            animation = Animatable(0f),
            startAngle = -90 + startAngle,
            color = it.color
        )
    }

    LaunchedEffect(arcs) {
        arcs.mapIndexed { index, it ->
            launch {
                it.animation.animateTo(
                    targetValue = it.targetSweepAngle,
                    animationSpec = tween(
                        durationMillis = 1500,
                        easing = FastOutSlowInEasing,
                        delayMillis = 200
                    ),
                )
            }
        }
    }

    Canvas(
        modifier = localModifier
            .fillMaxSize()
    ) {
        val stroke = Stroke(
            width = 80f,
            cap = StrokeCap.Round
        )
        val padding = 270f

        arcs.map {
            drawArc(
                startAngle = it.startAngle,
                sweepAngle = it.animation.value,
                color = it.color,
                useCenter = false,
                style = stroke,
                size = Size(width = size.width - padding, height = size.width - padding),
                topLeft = Offset(x = padding / 2, y = padding / 2)
            )
        }
    }
}