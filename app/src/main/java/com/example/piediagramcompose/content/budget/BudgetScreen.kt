package com.example.piediagramcompose.content.budget

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.piediagramcompose.R
import com.example.piediagramcompose.mockData.categoriesList
import com.example.piediagramcompose.model.Category

@Composable
fun BudgetScreen(
    navController: NavController
) {
    var isBottomSheetVisible by remember { mutableStateOf(false) }

    val sliderValues = remember {
        mutableStateListOf(
            *categoriesList.map {
                it.amount.toFloat()
            }.toTypedArray()
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFAF8F5))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        HeaderSection { isBottomSheetVisible = true }
        RemainingPlanSection(amount = "$${sliderValues.sumOf { it.toInt() }}")
        CategorySection(categoriesList, sliderValues)
    }

    if (isBottomSheetVisible) {
        BottomSheet(onDismiss = { isBottomSheetVisible = false })
    }
}

@Composable
fun HeaderSection(onSettingsClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp)),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .wrapContentWidth()
                .background(Color.White)
                .clip(RoundedCornerShape(16.dp))
        ) {
            Surface(
                modifier = Modifier
                    .padding(vertical = 16.dp, horizontal = 16.dp),
                shape = RoundedCornerShape(20.dp),
                color = Color.LightGray
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_savings),
                    contentDescription = "Profile Picture",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(46.dp)
                        .padding(2.dp)
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Bessie Cooper",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(end = 16.dp)
            )

            Spacer(modifier = Modifier.weight(1f))

            IconButton(onClick = onSettingsClick) {
                Icon(
                    modifier = Modifier
                        .size(46.dp),
                    imageVector = Icons.Default.Settings,
                    contentDescription = "Settings",
                    tint = Color.Black
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
        }
    }
}

@Composable
fun RemainingPlanSection(amount: String) {
    var isMenuExpanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        contentAlignment = Alignment.TopStart
    ) {
        Column {
            TextButton(onClick = { isMenuExpanded = true }) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Remaining Plan",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color.Gray
                    )
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Dropdown Icon",
                        tint = Color.Gray,
                        modifier = Modifier
                            .size(36.dp)
                    )
                }
            }
            Text(
                text = amount,
                fontSize = 42.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }

        DropdownMenu(
            expanded = isMenuExpanded,
            onDismissRequest = { isMenuExpanded = false },
            modifier = Modifier.wrapContentSize(Alignment.TopStart)
        ) {
            DropdownMenuItem(
                text = { Text("Option 1") },
                onClick = { isMenuExpanded = false }
            )
            DropdownMenuItem(
                text = { Text("Option 2") },
                onClick = { isMenuExpanded = false }
            )
        }
    }
}

@Composable
fun CategorySection(categories: List<Category>, sliderValues: MutableList<Float>) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        categories.forEachIndexed { index, category ->
            CategoryCard(category, sliderValues, index)
        }
    }
}

@Composable
fun CategoryCard(category: Category, sliderValues: MutableList<Float>, index: Int) {
    CustomCardWithInvertedShape(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        color = category.color
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = category.name,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.White
                )
                Text(
                    text = "$${sliderValues[index].toInt()}",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            CustomSlider(
                value = sliderValues[index],
                onValueChange = { newValue ->
                    sliderValues[index] = newValue
                },
                valueRange = 0f..10000f,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                thumbColor = Color.White,
                activeTrackColor = Color.White,
                inactiveTrackColor = Color.LightGray,
                horizontalPadding = 16.dp,
                backgroundCircleColor = Color.Blue.copy(alpha = 0.2f)
            )
        }
    }
}

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

@Composable
fun BottomSheet(onDismiss: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.5f))
            .clickable { onDismiss() },
        contentAlignment = Alignment.BottomCenter
    ) {
        Surface(
            shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
            color = Color.White,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Settings", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                // Add settings content here
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewBudgetScreen() {
    // Создаем фиктивный NavController
    val navController = rememberNavController()
    BudgetScreen(navController = navController)
}
