package com.example.piediagramcompose.content

import android.view.MotionEvent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.input.pointer.positionChange
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

class ExampleTouch {
    @Composable
    fun TouchableCanvasElementForDetectTapGestures() {
        var lastTouchX by remember { mutableStateOf(30f) }
        var lastTouchY by remember { mutableStateOf(30f) }
        var isTouching by remember { mutableStateOf(false) }
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .pointerInput(Unit) {
                    detectDragGestures(
                        onDragStart = {
                            isTouching = true
                        },
                        onDrag = { change, _ ->
                            lastTouchX = change.position.x
                            lastTouchY = change.position.y
                            if (change.positionChange() != Offset.Zero) change.consume()
                        },
                        onDragEnd = {
                            isTouching = false
                        }
                    )
                }
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                if (isTouching) {
                    drawCircle(
                        color = Color.Black,
                        radius = 20.dp.toPx(),
                        center = Offset(lastTouchX, lastTouchY)
                    )
                }
            }
        }
    }

    @Preview
    @Composable
    fun TouchDetectTapGesturesPreview() {
        TouchableCanvasElementForDetectTapGestures()
    }

    @OptIn(ExperimentalComposeUiApi::class)
    @Composable
    fun TouchableCanvasElementForMotionEvent() {
        val lastTouchX = remember { mutableStateOf(30f) }
        val lastTouchY = remember { mutableStateOf(30f) }
        val isTouching = remember { mutableStateOf(false) }

        Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .pointerInteropFilter { motionEvent ->
                    when (motionEvent.actionMasked) {
                        MotionEvent.ACTION_DOWN -> {
                            isTouching.value = true
                            updatePosition(motionEvent, lastTouchX, lastTouchY)
                        }

                        MotionEvent.ACTION_MOVE -> {
                            updatePosition(motionEvent, lastTouchX, lastTouchY)
                        }

                        MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                            isTouching.value = false
                        }
                    }
                    true
                }
        ) {
            Canvas(modifier = Modifier) {
                if (isTouching.value) {
                    drawCircle(
                        color = Color.Black,
                        radius = 20.dp.toPx(),
                        center = Offset(lastTouchX.value, lastTouchY.value)
                    )
                }
            }
        }
    }

    private fun updatePosition(
        motionEvent: MotionEvent,
        lastTouchX: androidx.compose.runtime.MutableState<Float>,
        lastTouchY: androidx.compose.runtime.MutableState<Float>
    ) {
        lastTouchX.value = motionEvent.x
        lastTouchY.value = motionEvent.y
    }

    @Preview
    @Composable
    fun TouchPreview() {
        TouchableCanvasElementForMotionEvent()
    }
}