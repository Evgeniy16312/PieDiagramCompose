package com.example.piediagramcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.piediagramcompose.content.AnimatedGapPieChart
import com.example.piediagramcompose.content.FilterChipGroupMonths
import com.example.piediagramcompose.content.PieData
import com.example.piediagramcompose.content.SalesList
import com.example.piediagramcompose.content.SalesListItem
import com.example.piediagramcompose.content.pieDataPoints
import com.example.piediagramcompose.mockData.chipMonthsList
import com.example.piediagramcompose.mockData.colorsList
import com.example.piediagramcompose.mockData.pointsValue
import com.example.piediagramcompose.ui.theme.PieDiagramComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PieDiagramComposeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Content()
                }
            }
        }
    }
}

@Composable
fun Content() {
    val items: List<SalesList> = listOf(
        SalesList("cinema", "low 23$", "43$"),
        SalesList("sport", "low 76$", "673$"),
        SalesList("media", "medium 597$", "2234$"),
        SalesList("market", "low 77$", "467$"),
        SalesList("oil", "height 1111$", "6554$"),
    ).shuffled()
    var selectedItemIndex by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier
            .background(Color.White)
            .padding(
                start = 16.dp,
                end = 16.dp,
                top = 16.dp
            )
    ) {

        FilterChipGroupMonths(
            items = chipMonthsList,
            onSelectedChanged = {
                selectedItemIndex = it
            }
        )

        if (selectedItemIndex <= 11) {
            pieDataPoints = listOf(
                PieData(pointsValue.random(), color = colorsList[0]),
                PieData(pointsValue.random(), color = colorsList[1]),
                PieData(pointsValue.random(), color = colorsList[2]),
                PieData(pointsValue.random(), color = colorsList[3]),
                PieData(pointsValue.random(), color = colorsList[4]),
            )
            AnimatedGapPieChart(
                modifier = Modifier
                    .fillMaxWidth(),
                pieDataPoints
            )

            Spacer(
                modifier = Modifier
                    .padding(top = 46.dp)
            )

            SalesListComposable(items)
        }
    }
}

@Composable
fun SalesListComposable(items: List<SalesList>) {
    LazyColumn(
        modifier = Modifier
            .background(Color.White)
    ) {
        items(items) { item ->
            SalesListItem(item = item,
                onClick = {}
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PieDiagramComposeTheme {
        Content()
    }
}