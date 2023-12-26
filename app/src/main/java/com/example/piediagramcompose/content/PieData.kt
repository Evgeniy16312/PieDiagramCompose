package com.example.piediagramcompose.content

import androidx.compose.ui.graphics.Color
import com.example.piediagramcompose.mockData.colorsList
import com.example.piediagramcompose.mockData.pointsValue

data class PieData(
    val value: Int,
    val color: Color,
)

var pieDataPoints = listOf(
    PieData(pointsValue.random(), color = colorsList[0]),
    PieData(pointsValue.random(), color = colorsList[1]),
    PieData(pointsValue.random(), color = colorsList[2]),
    PieData(pointsValue.random(), color = colorsList[3]),
    PieData(pointsValue.random(), color = colorsList[4]),
)
