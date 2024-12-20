package com.example.piediagramcompose.example

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun MultiArcExample() {
    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(24.dp)
    ) {
        val arcData = listOf(
            ArcData(Color.Red, 45f, 90f),
            ArcData(Color.Green, 135f, 45f),
            ArcData(Color.Blue, 180f, 90f),
            ArcData(Color.Yellow, 270f, 120f),
            ArcData(Color.Magenta, 30f, 15f)
        )
        arcData.forEach { data ->
            drawArc(
                color = data.color,
                startAngle = data.startAngle,
                sweepAngle = data.sweepAngle,
                useCenter = true,
                topLeft = Offset(
                    x = 100f,
                    y = 600f
                ),
                size = Size(1000f, 1000f),
                style = Stroke(width = 15f)
            )
        }
    }
}

@Preview
@Composable
fun MultiArcPreview() {
    MultiArcExample()
}

data class ArcData(
    val color: Color,
    val startAngle: Float,
    val sweepAngle: Float
)