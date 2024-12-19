package com.example.piediagramcompose.content.budget

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CustomCardWithInvertedShape(
    modifier: Modifier = Modifier,
    color: Color,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(150.dp)
            .padding(16.dp)
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawCustomCardWithInvertedShape(this, color)
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            content()
        }
    }
}

private fun drawCustomCardWithInvertedShape(drawScope: DrawScope, color: Color) {
    val cardWidth = drawScope.size.width
    val cardHeight = drawScope.size.height

    val cornerRadius = 40f              // Радиус закруглений углов
    val cutoutWidth = cardWidth * 0.15f // Ширина "выреза"
    val cutoutHeight = 130f             // Высота "выреза"
    val shapeWidth = cutoutWidth * 0.4f // Ширина фигурки
    val shapeHeight = 50f               // Высота фигурки над вырезом
    val edgeRadius = 12f                // Радиус закругления краёв фигурки

    // Основной путь для формы карточки
    val path = Path().apply {
        moveTo(0f + cornerRadius, 0f)
        lineTo(cardWidth / 2 - cutoutWidth / 2, 0f)

        // Кривая для выреза
        quadraticBezierTo(
            cardWidth / 2, cutoutHeight,
            cardWidth / 2 + cutoutWidth / 2, 0f
        )

        lineTo(cardWidth - cornerRadius, 0f)
        quadraticBezierTo(cardWidth, 0f, cardWidth, cornerRadius)
        lineTo(cardWidth, cardHeight - cornerRadius)
        quadraticBezierTo(cardWidth, cardHeight, cardWidth - cornerRadius, cardHeight)
        lineTo(cornerRadius, cardHeight)
        quadraticBezierTo(0f, cardHeight, 0f, cardHeight - cornerRadius)
        lineTo(0f, cornerRadius)
        quadraticBezierTo(0f, 0f, cornerRadius, 0f)
        close()
    }

    // Путь для перевёрнутой фигурки с закруглёнными краями
    val invertedShapePath = Path().apply {
        moveTo(cardWidth / 2 - shapeWidth / 2 + edgeRadius, 0f) // Левое начало с учётом радиуса

        // Левое закругление
        quadraticBezierTo(
            cardWidth / 2 - shapeWidth / 2, 0f,
            cardWidth / 2 - shapeWidth / 2, edgeRadius
        )

        // Нижняя дуга
        quadraticBezierTo(
            cardWidth / 2, shapeHeight,
            cardWidth / 2 + shapeWidth / 2, edgeRadius
        )

        // Правое закругление
        quadraticBezierTo(
            cardWidth / 2 + shapeWidth / 2, 0f,
            cardWidth / 2 + shapeWidth / 2 - edgeRadius, 0f
        )
        close()
    }

    // Карточка с вырезом
    drawScope.drawPath(path = path, color = color)

    // Перевёрнутая фигурка с закруглёнными краями
    drawScope.drawPath(path = invertedShapePath, color = color)
}

@Composable
fun PreviewCustomCardWithInvertedShape() {
    CustomCardWithInvertedShape(
        modifier = Modifier
            .fillMaxWidth(),
        color = Color(0xFF8FE6E6),
        content = {}
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewCustomCardWithInvertedShapePreview() {
    PreviewCustomCardWithInvertedShape()
}

