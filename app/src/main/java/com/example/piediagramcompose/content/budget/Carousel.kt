package com.example.piediagramcompose.content.budget

import android.widget.Toast
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive

@Composable
fun Carousel(pageCount: Int) {
    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { pageCount }
    )

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        ) { page ->
            CarouselItem(index = page)
        }

        Spacer(modifier = Modifier.height(8.dp))

        PagerIndicator(
            pagerState = pagerState,
            pageCount = pageCount
        )
    }
}

@Composable
fun CarouselItem(index: Int) {
    val imageUrl = remember { mutableStateOf<String?>(null) }
    val isLoading = remember { mutableStateOf(true) }
    val isError = remember { mutableStateOf(false) }
    val context = LocalContext.current

    LaunchedEffect(index) {
        isLoading.value = true
        isError.value = false
        try {
            val randomImageUrl = fetchRandomImageUrl()
            if (isActive) {
                imageUrl.value = randomImageUrl
                isLoading.value = false
            }
        } catch (e: Exception) {
            if (isActive) {
                isError.value = true
                Toast.makeText(context, "Error loading image: ${e.message}", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(8.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(
                color = Color.Gray.copy(alpha = 0.2f),
                shape = RoundedCornerShape(8.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        when {
            isLoading.value -> {
                ShimmerPlaceholder(modifier = Modifier.fillMaxSize())
            }

            isError.value -> {
                Text(
                    text = "Failed to load image",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Red
                )
            }

            else -> {
                AsyncImage(
                    model = imageUrl.value,
                    contentDescription = "Random Image",
                    modifier = Modifier.fillMaxSize(),
                    alignment = Alignment.Center,
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}

@Composable
fun PagerIndicator(pagerState: PagerState, pageCount: Int) {
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        repeat(pageCount) { index ->
            Box(
                modifier = Modifier
                    .size(if (index == pagerState.currentPage) 12.dp else 8.dp)
                    .padding(4.dp)
                    .background(
                        color = if (index == pagerState.currentPage) MaterialTheme.colorScheme.primary
                        else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f),
                        shape = CircleShape
                    )
            )
        }
    }
}

@Composable
fun ShimmerPlaceholder(modifier: Modifier = Modifier) {
    val infiniteTransition = rememberInfiniteTransition(label = "")

    val shimmerTranslate by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ), label = ""
    )

    val shimmerBrush = Brush.linearGradient(
        colors = listOf(
            Color.Gray.copy(alpha = 0.3f),
            Color.Gray.copy(alpha = 0.1f),
            Color.Gray.copy(alpha = 0.3f)
        ),
        start = Offset(0f, 0f),
        end = Offset(shimmerTranslate * 1000f, shimmerTranslate * 1000f)
    )

    Box(
        modifier = modifier
            .background(shimmerBrush, shape = RoundedCornerShape(8.dp))
    )
}

suspend fun fetchRandomImageUrl(): String {
    try {
        delay(1000)
        return "https://placebear.com/g/1200/1200"
    } catch (e: Exception) {
        throw Exception("Failed to fetch image URL")
    }
}