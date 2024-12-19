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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.piediagramcompose.content.diagram.FilterChipGroupMonths
import com.example.piediagramcompose.content.diagram.MyContent
import com.example.piediagramcompose.mockData.ListValueParts
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
                            var selectedItemIndex by remember { mutableIntStateOf(0) }
                            val valueOne = (300 until 400).random()
                            val valueTwo = (150 until 200).random()
                            val valueThree = (200 until 300).random()
                            val valueFour = (50 until 100).random()
                            val valueFive = (400 until 500).random()
                            val valuePartOne = (25 until 30).random().toFloat()
                            val valuePartTwo = (15 until 20).random().toFloat()
                            val valuePartThree = (20 until 25).random().toFloat()
                            val valuePartFour = (10 until 15).random().toFloat()
                            val valuePartFive = (30 until 35).random().toFloat()

                            FilterChipGroupMonths(
                                items = chipMonthsList,
                                onSelectedChanged = {
                                    selectedItemIndex = it
                                }
                            )
                            if (selectedItemIndex <= 11) {
                                MyContent(
                                    chipMonthsList[selectedItemIndex],
                                    valueOne = valueOne,
                                    valueTwo = valueTwo,
                                    valueThree = valueThree,
                                    valueFour = valueFour,
                                    valueFive = valueFive,
                                    ListValueParts(
                                        valuePartOne,
                                        valuePartTwo,
                                        valuePartThree,
                                        valuePartFour,
                                        valuePartFive
                                    ),
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        PieDiagramComposeTheme {
            MyContent(
                "",
                valueOne = 100,
                valueTwo = 1000,
                valueThree = 1000,
                valueFour = 100,
                valueFive = 100,
                listOf(20f, 20f, 10f, 30f, 10f)
            )
        }
    }
}