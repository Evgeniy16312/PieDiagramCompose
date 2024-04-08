package com.example.piediagramcompose.content

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.TargetBasedAnimation
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.withFrameNanos
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.piediagramcompose.mockData.colorsList
import com.example.piediagramcompose.mockData.populateList

@Composable
fun PieChart(
    data: List<Int>,
    centerMonth: String,
    centerSum: String,
    percentValue: List<String>,
    radiusOuter: Dp = 110.dp,
    chartBarWidth: List<Float>,
    animDuration: Int = 1000,
) {
    val totalSum = data.sum()
    val floatValue = mutableListOf<Float>()
    val dotRects = ArrayList<Rect>()

    data.forEachIndexed { index, value ->
        floatValue.add(index, 360 * value.toFloat() / totalSum.toFloat())
    }

    var animationPlayed by remember {
        mutableStateOf(false)
    }

    var lastValue = 0f

    val animateSize by animateFloatAsState(
        targetValue =
        if (animationPlayed) radiusOuter.value * 2f else 0f,
        animationSpec = tween(
            durationMillis = animDuration,
            delayMillis = 0,
            easing = LinearOutSlowInEasing
        ),
        label = ""
    )

    val animateRotation by animateFloatAsState(
        targetValue = if (animationPlayed) 90f * 11f else 0f,
        animationSpec = tween(
            durationMillis = animDuration,
            delayMillis = 0,
            easing = LinearOutSlowInEasing
        ),
        label = ""
    )

    val animateText by animateFloatAsState(
        targetValue = if (animationPlayed) 360f else 0f,
        animationSpec = tween(
            durationMillis = animDuration,
            delayMillis = 0,
            easing = LinearOutSlowInEasing
        ),
        label = ""
    )

    LaunchedEffect(key1 = true) {
        animationPlayed = true
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 40.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier.size(animateSize.dp),
            contentAlignment = Alignment.Center
        ) {
            Canvas(
                modifier = Modifier
                    .size(radiusOuter * 2f)
                    .rotate(animateRotation)
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onTap = { tapOffset ->
                                var index = 0
                                for (rect in dotRects) {
                                    if (rect.contains(tapOffset)) {
                                        break
                                    }
                                    index++
                                }
                            }
                        )
                    }
            ) {
                floatValue.forEachIndexed { index, value ->
                    drawArc(
                        color = colorsList[index],
                        startAngle = lastValue,
                        sweepAngle = value - 14f,
                        useCenter = false,
                        style = Stroke(
                            width = chartBarWidth[index],
                            cap = StrokeCap.Round
                        )
                    )
                    lastValue += value
                }
            }
            CenterText(
                mounts = centerMonth,
                centerSum = centerSum
            )

            SalesMoneyInPercent(
                animateText,
                95,
                -95,
                percentValue.random()
            )

            SalesMoneyInPercent(
                animateText,
                -95,
                -95,
                percentValue.random()
            )

            SalesMoneyInPercent(
                animateText,
                -95,
                95,
                percentValue.random()
            )

            SalesMoneyInPercent(
                animateText,
                105,
                80,
                percentValue.random()
            )

            SalesMoneyInPercent(
                animateText,
                0,
                125,
                percentValue.random()
            )
        }
    }

    Spacer(modifier = Modifier.padding(top = 60.dp))

    SalesListComposable(populateList())
}

@Composable
fun SalesListComposable(items: List<SalesList>) {
    val state by remember { mutableStateOf(false) }
    val anim = remember {
        TargetBasedAnimation(
            animationSpec = tween(durationMillis = 3500),
            typeConverter = Float.VectorConverter,
            initialValue = 0f,
            targetValue = 700f,
        )
    }
    var playTime by remember { mutableStateOf(0L) }
    var animationValue by remember { mutableStateOf(0) }

    LaunchedEffect(state) {
        val startTime = withFrameNanos { it }
        do {
            playTime = withFrameNanos { it } - startTime
            animationValue = anim.getValueFromNanos(playTime).toInt()
        } while (!anim.isFinishedFromNanos(playTime))
    }

    LazyColumn(
        modifier = Modifier
            .background(Color.White)
            .size(animationValue.dp),
    ) {
        items(items) { item ->
            SalesListItem(item = item,
                modifier = Modifier,
                onClick = {
                }
            )
        }
    }
}


@Composable
fun SalesMoneyInPercent(animateText: Float, x: Int, y: Int, text: String) {
    Box(
        modifier = Modifier
            .offset(x = (x).dp, y = (y).dp)
            .size(40.dp)
            .background(
                color = Color.White,
                shape = RoundedCornerShape(10.dp)
            )
            .rotate(animateText),
        contentAlignment = Alignment.Center
    )
    {
        Text(
            text = text,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            color = Color.Black
        )
    }
}

@Composable
fun CenterText(
    mounts: String,
    centerSum: String,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.padding(4.dp),
            text = mounts,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            color = Color.Gray
        )
        Text(
            modifier = Modifier.padding(4.dp),
            text = centerSum,
            fontWeight = FontWeight.Medium,
            fontSize = 24.sp,
            color = Color.Black
        )
    }
}