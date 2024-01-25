package com.example.piediagramcompose.mockData

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.example.piediagramcompose.content.PieData

class ListPieProvider : PreviewParameterProvider<List<PieData>> {
    override val values = sequenceOf(
        pieDataPoints()
    )
}

fun pieDataPoints() = listOf(
    PieData(pointsValue.random(), color = colorsList[0]),
    PieData(pointsValue.random(), color = colorsList[1]),
    PieData(pointsValue.random(), color = colorsList[2]),
    PieData(pointsValue.random(), color = colorsList[3]),
    PieData(pointsValue.random(), color = colorsList[4]),
)