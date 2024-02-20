package com.example.piediagramcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.piediagramcompose.content.FilterChipGroupMonths
import com.example.piediagramcompose.content.PieChart
import com.example.piediagramcompose.mockData.chartBarWidth
import com.example.piediagramcompose.mockData.chipMonthsList
import com.example.piediagramcompose.ui.theme.Background
import com.example.piediagramcompose.ui.theme.PieDiagramComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PieDiagramComposeTheme {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center
                ) {

                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Background)
                    ) {
                        Column(
                            modifier = Modifier
                                .background(Background)
                                .padding(
                                    start = 16.dp,
                                    end = 16.dp,
                                    top = 16.dp
                                )
                        ) {
                            Content()
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun Content() {
        var selectedItemIndex by remember { mutableStateOf(0) }
        val sumCenter = (1000 until 2000).random()
        val selectedMonths = "${sumCenter}$"
        val percent = listOf(
            (10 until 30).random(),
            (10 until 30).random(),
            (10 until 30).random(),
            (10 until 30).random(),
            (10 until 30).random()
        )

        val percentList = listOf(
            "${percent[0]}%",
            "${percent[1]}%",
            "${percent[2]}%",
            "${percent[3]}%",
            "${percent[4]}%",
        )

        val pointsValueList = listOf(
            (100 until 150).random(),
            (100 until 150).random(),
            (100 until 150).random(),
            (100 until 150).random(),
            (100 until 150).random()
        )

        Column(
            modifier = Modifier
                .background(Background)
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
                PieChart(
                    data = pointsValueList,
                    centerMonth = chipMonthsList[selectedItemIndex],
                    centerSum = selectedMonths,
                    percentValue = percentList,
                    chartBarWidth = chartBarWidth
                )
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        PieDiagramComposeTheme {
            MainScreenContent()
        }
    }
}