package com.example.piediagramcompose.content.budget

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.piediagramcompose.R
import com.example.piediagramcompose.mockData.categoriesList
import com.example.piediagramcompose.model.Category
import com.example.piediagramcompose.ui.theme.Background

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BudgetScreen(
    navController: NavController
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var isSheetVisible by remember { mutableStateOf(false) }

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
            .background(Background)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        HeaderSection {
            isSheetVisible = true
        }
        RemainingPlanSection(
            amount = stringResource(
                R.string.rub,
                sliderValues.sumOf { it.toInt() })
        )
        CategorySection(categoriesList, sliderValues)
    }

    if (isSheetVisible) {
        BottomSheet(
            onDismiss = { isSheetVisible = false },
            sheetState = sheetState
        )
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
                        .size(36.dp)
                        .padding(2.dp)
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = stringResource(R.string.fio),
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(end = 16.dp)
                    .fillMaxWidth(0.7f)
            )

            Spacer(modifier = Modifier.weight(1f))

            IconButton(onClick = onSettingsClick) {
                Icon(
                    modifier = Modifier
                        .size(36.dp),
                    imageVector = Icons.Default.Settings,
                    contentDescription = stringResource(R.string.setting),
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
                        text = stringResource(R.string.remaining_plan),
                        fontSize = 28.sp,
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
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier
                    .padding(start = 12.dp)
            )
        }

        DropdownMenu(
            expanded = isMenuExpanded,
            onDismissRequest = { isMenuExpanded = false },
            modifier = Modifier.wrapContentSize(Alignment.TopStart)
        ) {
            DropdownMenuItem(
                text = { Text(stringResource(R.string.option_1)) },
                onClick = { isMenuExpanded = false }
            )
            DropdownMenuItem(
                text = { Text(stringResource(R.string.option_2)) },
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
                    text = stringResource(R.string.rub, sliderValues[index].toInt()),
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheet(
    onDismiss: () -> Unit,
    sheetState: SheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
) {
    ModalBottomSheet(
        onDismissRequest = { onDismiss() },
        sheetState = sheetState,
        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        containerColor = Color.White
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = stringResource(R.string.setting),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            // Carousel component
            Carousel(pageCount = 5)
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