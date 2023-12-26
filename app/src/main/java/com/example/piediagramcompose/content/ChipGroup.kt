package com.example.piediagramcompose.content

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.piediagramcompose.mockData.chipMonthsList
import com.example.piediagramcompose.ui.theme.OrangeLight
import com.example.piediagramcompose.ui.theme.PieDiagramComposeTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterChipGroupMonths(
    items: List<String>,
    defaultSelectedItemIndex: Int = 0,
    selectedItemIcon: ImageVector = Icons.Filled.Done,
    onSelectedChanged: (Int) -> Unit = {}
) {
    var selectedItemIndex by remember { mutableStateOf(defaultSelectedItemIndex) }

    LazyRow(
        userScrollEnabled = true,
        modifier = Modifier
            .padding(4.dp, 4.dp)
    ) {

        items(items.size) { index: Int ->
            FilterChip(
                modifier = Modifier
                    .padding(end = 6.dp)
                    .height(42.dp),
                selected = items[selectedItemIndex] == items[index],
                onClick = {
                    selectedItemIndex = index
                    onSelectedChanged(index)
                },
                label = {
                    Text(
                        text = items[index],
                        color = Color.Black,
                        fontSize = 18.sp
                    )
                },
                shape = RoundedCornerShape(16),
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = OrangeLight
                ),
                border = FilterChipDefaults.filterChipBorder(
                    borderColor = Color.White
                ),

                leadingIcon = {
                    if (items[selectedItemIndex] == items[index]) {
                        Icon(
                            imageVector = selectedItemIcon,
                            contentDescription = "description",
                            modifier = Modifier.size(FilterChipDefaults.IconSize)
                        )
                    }
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PieDiagramComposeTheme {
        FilterChipGroupMonths(items = chipMonthsList,
            onSelectedChanged = {

            }
        )
    }
}