package com.example.piediagramcompose.content.diagram

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
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
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.CornerRadius
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
import com.example.piediagramcompose.R
import com.example.piediagramcompose.mockData.colorsList
import com.example.piediagramcompose.mockData.populateList
import com.example.piediagramcompose.model.SalesList
import com.example.piediagramcompose.ui.theme.Background
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun PieChart(
    modifier: Modifier = Modifier,
    selectedMonths: String,
    valueOne: Int,
    valueTwo: Int,
    valueThree: Int,
    valueFour: Int,
    valueFive: Int,
    radiusOuter: Dp = 110.dp,
    animDuration: Int = 1000,
    parts: List<Float>,
    onPartClick: (Int, Color) -> Unit,
) {
    val strokeWidth = 50.dp
    val strokeWidthClick = 95.dp
    val space = 7.dp
    val textPaint = Paint().asFrameworkPaint().apply {
        isAntiAlias = true
        textSize = 40f
        color = android.graphics.Color.BLACK
    }

    val textPaintCenterTop = Paint().asFrameworkPaint().apply {
        isAntiAlias = true
        textSize = 60f
        color = android.graphics.Color.DKGRAY
    }

    val textPaintCenterBottom = Paint().asFrameworkPaint().apply {
        isAntiAlias = true
        textSize = 60f
        color = android.graphics.Color.BLACK
    }

    val total = parts.sum()
    val angles = parts.map { it / total * 360 }

    var colorPart by remember { mutableStateOf(Background) }
    var rotationAngle by remember { mutableFloatStateOf(0f) }

    var selectedPart by remember { mutableIntStateOf(-1) }
    var previousSelectedPart by remember { mutableIntStateOf(-1) }

    val strokeWidthAnimates = remember(parts.size) {
        parts.map { Animatable(strokeWidth.value) }
    }

    var animationPlayed by remember {
        mutableStateOf(false)
    }

    var currentMonths by remember { mutableStateOf(selectedMonths) }

    val animateSize by animateFloatAsState(
        targetValue = radiusOuter.value * 2f,
        animationSpec = tween(
            durationMillis = animDuration,
            easing = LinearOutSlowInEasing
        ),
        finishedListener = {
            // Анимация закончена, можно обновить состояние
            previousSelectedPart = selectedPart
        }, label = ""
    )

    val animateRotation by animateFloatAsState(
        targetValue = rotationAngle,
        animationSpec = tween(
            durationMillis = animDuration,
            easing = LinearOutSlowInEasing
        ), label = ""
    )

    // Проверка на смену месяца и сброс значений
    LaunchedEffect(selectedMonths) {
        if (currentMonths != selectedMonths) {
            selectedPart = -1 // Очищаем выбранный элемент
            colorPart = Background // Сбрасываем цвет выбранного элемента
            previousSelectedPart = -1 // Сбрасываем предыдущий выбранный элемент
            currentMonths = selectedMonths // Обновляем текущий месяц
            animationPlayed = false // Сбрасываем состояние анимации
            // Сброс ширины всех stroke к начальным значениям
            strokeWidthAnimates.forEach { anim ->
                anim.snapTo(strokeWidth.value)
            }
        }
    }

// Запуск анимации при старте приложения
    LaunchedEffect(true) {
        rotationAngle += 360f
    }
// Запуск анимации при смене месяца
    LaunchedEffect(selectedMonths) {
        rotationAngle += 360f
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
                            if (selectedPart != index) {
                                previousSelectedPart = selectedPart
                                selectedPart = index
                                val selectedColor = colorsList[index]
                                onPartClick(index, selectedColor)
                                colorPart = selectedColor
                            }
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
                        width = strokeWidthAnimates[i].value,
                        cap = StrokeCap.Round
                    )
                )
                // Радиус, на котором должен находиться текст
                val labelRadius = size.minDimension / 2f
                val textAngleDegrees = startAngle + sweepAngle / 2 - space.toPx() / 2
                val textAngleRadians = Math.toRadians(textAngleDegrees.toDouble())
                val labelX = center.x + labelRadius * cos(textAngleRadians).toFloat()
                val labelY = center.y + labelRadius * sin(textAngleRadians).toFloat()

                // Отрисовка контейнера где лежат значения занятой доли в каждой части канваса
                drawRoundRect(
                    Color.White,
                    topLeft = Offset(labelX + 25, labelY),
                    size = Size(160f, 90f),
                    cornerRadius = CornerRadius(16f)
                )
                // Отрисовка значения занятой доли в каждой части канваса
                drawContext.canvas.nativeCanvas.drawText(
                    "${parts[i]}%",
                    labelX + 55,
                    labelY + 55,
                    textPaint
                )
                // Отрисовка текста в центре канваса
                drawIntoCanvas {
                    val textBounds = android.graphics.Rect()
                    textPaintCenterTop.getTextBounds(
                        selectedMonths,
                        0,
                        selectedMonths.length,
                        textBounds
                    )
                    it.nativeCanvas.drawText(
                        selectedMonths,
                        size.width / 2f - textBounds.exactCenterX(),
                        size.height / 2f + textBounds.exactCenterY(),
                        textPaintCenterTop
                    )
                }
                drawIntoCanvas {
                    val textBounds = android.graphics.Rect()
                    textPaintCenterBottom.getTextBounds(
                        selectedMonths,
                        0,
                        selectedMonths.length,
                        textBounds
                    )
                    it.nativeCanvas.drawText(
                        (valueOne + valueTwo + valueThree + valueFour + valueFive
                                * 5 + 1050).toString() + " Pуб.",
                        size.width / 2f - textBounds.exactCenterX(),
                        size.height / 1.63f + textBounds.exactCenterY(),
                        textPaintCenterBottom
                    )
                }
                startAngle += sweepAngle
            }
        }
    }

// Запуск анимации изменения толщины stroke после обновления состояния
// selectedPart и previousSelectedPart
    LaunchedEffect(selectedPart) {
        if (previousSelectedPart != -1 && previousSelectedPart != selectedPart) {
            strokeWidthAnimates[previousSelectedPart].animateTo(
                targetValue = strokeWidth.value,
                animationSpec = tween(
                    durationMillis = 300,
                    easing = LinearOutSlowInEasing
                )
            )
        }
        if (selectedPart != -1) {
            strokeWidthAnimates[selectedPart].animateTo(
                targetValue = strokeWidthClick.value,
                animationSpec = tween(
                    durationMillis = 300,
                    easing = LinearOutSlowInEasing
                )
            )
        }
    }

    SalesListComposable(
        populateList(valueOne, valueTwo, valueThree, valueFour, valueFive),
        color = colorPart,
        selected = selectedPart,
        onClick = {}
    )
}

@Composable
fun SalesListComposable(
    items: List<SalesList>,
    color: Color,
    selected: Int,
    onClick: () -> Unit,
) {
    val selectedColor by remember { mutableStateOf(Color.White) }
    var visible by remember { mutableStateOf(false) }
    val transition = updateTransition(visible, label = "visibility")
    val offsetY = transition.animateDp(
        transitionSpec = {
            tween(durationMillis = 1000)
        }, label = ""
    ) { visibility ->
        if (visibility) 0.dp else (500).dp
    }
    val iconList = remember {
        mutableListOf(
            R.drawable.ic_badge,
            R.drawable.ic_laptop,
            R.drawable.ic_savings,
            R.drawable.ic_surprize,
        ).shuffled()
    } // Перемешиваем список иконок один раз

    LaunchedEffect(visible) {
        visible = true
    }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        LazyColumn(
            modifier = Modifier.padding(top = offsetY.value)
        ) {
            itemsIndexed(items) { index, item ->
                AnimatedVisibility(
                    visible = visible,
                    enter = slideInVertically(
                        initialOffsetY = { -500 }
                    ) + fadeIn(),
                    exit = slideOutVertically() + fadeOut(),
                ) {
                    SalesListItem(item = item,
                        icon = iconList[index % iconList.size],
                        color = animateColorAsState(
                            targetValue = if (index == selected) color else selectedColor,
                            animationSpec = tween(durationMillis = 1000),
                            label = ""
                        ).value,
                        onClick = {
                            onClick()
                        }
                    )
                }
            }
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
fun MyContent(
    months: String,
    valueOne: Int,
    valueTwo: Int,
    valueThree: Int,
    valueFour: Int,
    valueFive: Int,
    parts: List<Float>
) {
    var clickedPart by remember { mutableStateOf("") }
    var selectedColor by remember { mutableStateOf(Background) }

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
            valueOne = valueOne,
            valueTwo = valueTwo,
            valueThree = valueThree,
            valueFour = valueFour,
            valueFive = valueFive
        ) { _, color ->
            clickedPart = months
            selectedColor = color
        }
    }
}