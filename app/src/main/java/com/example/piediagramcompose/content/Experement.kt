package com.example.piediagramcompose.content


import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ClippingExample() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier
            .size(800.dp)
            .background(Color.LightGray)
        ) {
            // Ограничиваем область рисования прямоугольником
            clipRect(
                left = 100f,
                top = 100f,
                right = 700f,
                bottom = 700f
            ) {
                // Рисуем что-нибудь внутри этого прямоугольника
                drawIntoCanvas { canvas ->
                    val paint = Paint().apply {
                        color = Color.Red
                        isAntiAlias = true
                    }
                    canvas.drawRect(
                        Rect(
                            100f,
                            100f,
                            700f,
                            700f
                        ), paint
                    )
                }
            }
            // Ограничиваем область рисования произвольным путём (звезда)
            clipPath(Path().apply {
                moveTo(400f, 200f)
                lineTo(480f, 500f)
                lineTo(700f, 500f)
                lineTo(520f, 600f)
                lineTo(600f, 800f)
                lineTo(400f, 660f)
                lineTo(200f, 800f)
                lineTo(280f, 600f)
                lineTo(100f, 500f)
                lineTo(320f, 500f)
                close()
            }) {
                // Рисуем что-нибудь внутри этого пути
                drawIntoCanvas { canvas ->
                    val paint = Paint().apply {
                        color = Color.Blue
                        isAntiAlias = true
                    }
                    canvas.drawPath(Path().apply {
                        moveTo(400f, 200f)
                        lineTo(480f, 500f)
                        lineTo(700f, 500f)
                        lineTo(520f, 600f)
                        lineTo(600f, 800f)
                        lineTo(400f, 660f)
                        lineTo(200f, 800f)
                        lineTo(280f, 600f)
                        lineTo(100f, 500f)
                        lineTo(320f, 500f)
                        close()
                    }, paint)
                }
            }
            // Ограничиваем область рисования кругом
            clipPath(Path().apply {
                addOval(
                    Rect(
                        200f,
                        200f,
                        600f,
                        600f
                    )
                )
            }) {
                // Рисуем что-нибудь внутри этого пути
                drawIntoCanvas { canvas ->
                    val paint = Paint().apply {
                        color = Color.Green
                        isAntiAlias = true
                    }
                    canvas.drawCircle(
                        center = Offset(
                            400f,
                            400f
                        ),
                        radius = 200f,
                        paint
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ClippingExamplePreview() {
    ClippingExample()
}