package com.example.piediagramcompose.content

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import com.example.piediagramcompose.mockData.colorsList
import com.example.piediagramcompose.mockData.pointsValue


@Composable
fun PieCharts(
    pointsValue: List<Float>,
    colorsList: List<Color>
) {
    var startAngle = 270f
    val spice = 15f

    val total = pointsValue.sum()
    val proportions = pointsValue.map {
        it * 100 / total
    }

    val sweepAnglePercentage = proportions.map {
        360 * it / 100
    }

    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

        sweepAnglePercentage.forEachIndexed { index, sweepAngle ->
            DrawArc(
                color = colorsList[index],
                startAngle = startAngle,
                sweepAngle = sweepAngle - spice,
                widthStroke = if (pointsValue[index] >= 100f) {
                    120f
                } else if (pointsValue[index] >= 70f) {
                    110f
                } else if (pointsValue[index] >= 40f) {
                    100f
                } else if (pointsValue[index] >= 30f) {
                    80f
                } else if (pointsValue[index] >= 20f) {
                    70f
                } else if (pointsValue[index] >= 10f) {
                    80f
                } else {
                    75f
                }
            )

            startAngle += sweepAngle
        }
    }
}

fun DrawScope.DrawArc(
    color: Color,
    startAngle: Float,
    sweepAngle: Float,
    widthStroke: Float
) {
    val padding = 250f

    drawArc(
        color = color,
        startAngle = startAngle,
        sweepAngle = sweepAngle,
        useCenter = false,
        size = Size(width = size.width - padding, height = size.width - padding),
        style = Stroke(
            width = widthStroke,
            cap = StrokeCap.Round,
            join = StrokeJoin.Round
        ),
        topLeft = Offset(x = padding / 2, y = padding / 2)
    )
}

@Preview
@Composable
fun PreviewPie() {
    PieCharts(
        pointsValue = pointsValue,
        colorsList = colorsList
    )
}
