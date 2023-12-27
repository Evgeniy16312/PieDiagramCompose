package com.example.piediagramcompose.content

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.piediagramcompose.R
import com.example.piediagramcompose.ui.theme.OrangeLight
import com.example.piediagramcompose.ui.theme.PurpleLight


@Composable
fun SalesListItem(
    item: SalesList,
    onClick: () -> Unit,
) {
    val iconList = mutableListOf(
        R.drawable.ic_badge,
        R.drawable.ic_laptop,
        R.drawable.ic_savings,
        R.drawable.ic_surprize,
    )
    Box {
        Row(
            modifier = Modifier
                .clickable(onClick = onClick)
                .clip(RoundedCornerShape(4.dp))
                .fillMaxSize()
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {

            Image(
                modifier = Modifier
                    .size(96.dp, 54.dp),
                painter = painterResource(id = iconList.random()),
                contentDescription = "Content",
                alignment = Alignment.Center
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    modifier = Modifier

                ) {
                    Text(
                        text = item.categoriesCost,
                        maxLines = 1,
                        fontSize = 22.sp,
                        overflow = TextOverflow.Ellipsis,
                        color = PurpleLight,
                        style = MaterialTheme.typography.bodyMedium
                    )

                    Text(
                        text = item.averageCost,
                        maxLines = 1,
                        fontSize = 22.sp,
                        overflow = TextOverflow.Ellipsis,
                        color = OrangeLight,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                Text(
                    modifier = Modifier
                        .padding(end = 16.dp)
                        .align(Alignment.CenterEnd),
                    text = item.cost,
                    maxLines = 1,
                    fontSize = 22.sp,
                    overflow = TextOverflow.Ellipsis,
                    color = OrangeLight,
                    style = MaterialTheme.typography.bodyMedium
                )

            }
        }
    }
}