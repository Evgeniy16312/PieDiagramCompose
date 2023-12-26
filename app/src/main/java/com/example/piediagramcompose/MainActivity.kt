package com.example.piediagramcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
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
        SalesList("market", "low250$", "222$"),
        SalesList("market", "low250$", "222$"),
        SalesList("market", "low250$", "222$"),
    )
    var selectedItemIndex by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier
            .background(Color.White)
            .padding(vertical = 16.dp, horizontal = 16.dp)
    ) {

        FilterChipGroupMonths(
            items = chipMonthsList,
            onSelectedChanged = {
                selectedItemIndex = it
            }
        )

        Spacer(
            modifier = Modifier
                .padding(top = 24.dp)
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
                    .padding(16.dp)
                    .fillMaxWidth()
                    .height(300.dp),
                pieDataPoints
            )
        }

        Spacer(
            modifier = Modifier
                .padding(top = 24.dp)
        )

        SalesListComposable(items)

    }
}

@Composable
fun SalesListComposable(items: List<SalesList>) {
    LazyColumn {
        items(items) { item ->
            SalesListItem(item = item,
                onClick = {})
            Divider(color = Color.Black)
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