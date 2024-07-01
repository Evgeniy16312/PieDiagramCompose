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
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
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

@Composable
fun CanvasExample() {
    Canvas(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        // Прямоугольник
        drawRect(
            color = Color.Red,
            topLeft = Offset(50f, 150f),
            size = Size(100f, 150f)
        )

        // Овал
        drawOval(
            color = Color.Green,
            topLeft = Offset(200f, 150f),
            size = Size(150f, 100f)
        )

        // Круг
        drawCircle(
            color = Color.Blue,
            radius = 50f,
            center = Offset(450f, 200f)
        )

        // Линия
        drawLine(
            color = Color.Magenta,
            start = Offset(600f, 150f),
            end = Offset(700f, 300f),
            strokeWidth = 5f
        )

        // Текст
        drawContext.canvas.nativeCanvas.apply {
            val textPaint = android.graphics.Paint().apply {
                color = android.graphics.Color.BLACK
                textSize = 50f
            }
            drawText("Hello, Compose!", 50f, 450f, textPaint)
        }

        // Путь
        val path = Path().apply {
            moveTo(50f, 500f)
            lineTo(100f, 600f)
            lineTo(150f, 550f)
            lineTo(200f, 650f)
            close()
        }
        drawPath(
            path = path,
            color = Color.Cyan,
            style = Stroke(width = 5f)
        )

        // Дуга
        drawArc(
            color = Color.Black,
            startAngle = 0f,
            sweepAngle = 180f,
            useCenter = false,
            topLeft = Offset(300f, 500f),
            size = Size(100f, 100f)
        )

        // Скругленный прямоугольник
        drawRoundRect(
            color = Color.Gray,
            topLeft = Offset(450f, 500f),
            size = Size(150f, 100f),
            cornerRadius = CornerRadius(20f, 20f)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CanvasExamplePreview() {
    CanvasExample()
}