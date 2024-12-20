package com.example.piediagramcompose.content.budget

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun CustomSlider(
    value: Float,
    onValueChange: (Float) -> Unit,
    valueRange: ClosedFloatingPointRange<Float> = 0f..1f,
    modifier: Modifier,
    thumbColor: Color,
    activeTrackColor: Color,
    inactiveTrackColor: Color,
    horizontalPadding: Dp,
    backgroundCircleColor: Color = Color.Black.copy(alpha = 0.1f) // Цвет прозрачного круга
) {
    BoxWithConstraints(
        modifier = modifier
            .height(48.dp) // Увеличенная высота для круга
            .padding(horizontal = horizontalPadding) // Горизонтальный паддинг
    ) {
        val trackHeight = 3.dp
        val thumbRadius = 8.dp
        val backgroundCircleRadius = 16.dp

        val maxWidth = constraints.maxWidth.toFloat()
        val progress =
            ((value - valueRange.start) / (valueRange.endInclusive - valueRange.start)).coerceIn(
                0f,
                1f
            )

        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            // Пунктирная неактивная дорожка
            drawLine(
                color = inactiveTrackColor,
                start = Offset(0f, center.y),
                end = Offset(size.width, center.y),
                strokeWidth = trackHeight.toPx(),
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(30f, 10f), phase = 0f)
            )

            // Активная дорожка
            drawLine(
                color = activeTrackColor,
                start = Offset(0f, center.y),
                end = Offset(size.width * progress, center.y),
                strokeWidth = trackHeight.toPx()
            )

            // Прозрачный фон круга
            drawCircle(
                color = backgroundCircleColor,
                radius = backgroundCircleRadius.toPx(),
                center = Offset(size.width * progress, center.y)
            )

            // Ползунок
            drawCircle(
                color = thumbColor,
                radius = thumbRadius.toPx(),
                center = Offset(size.width * progress, center.y)
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .pointerInput(Unit) {
                    detectDragGestures { change, _ ->
                        val newProgress = (change.position.x / maxWidth).coerceIn(0f, 1f)
                        val newValue =
                            valueRange.start + newProgress * (valueRange.endInclusive - valueRange.start)
                        onValueChange(newValue)
                    }
                }
        )
    }
}