package com.example.piediagramcompose.content

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.piediagramcompose.R
import com.example.piediagramcompose.ui.theme.Background
import com.example.piediagramcompose.ui.theme.GreyLight


@Composable
fun SalesListItem(
    item: SalesList,
    modifier: Modifier,
    onClick: () -> Unit,
) {
    val iconList = mutableListOf(
        R.drawable.ic_badge,
        R.drawable.ic_laptop,
        R.drawable.ic_savings,
        R.drawable.ic_surprize,
    )
    Box(
        modifier = Modifier
            .background(Background)
            .padding(
                vertical = 8.dp,
                horizontal = 8.dp
            )
            .height(80.dp)
            .clip(RoundedCornerShape(16.dp))

    ) {
        Row(
            modifier = Modifier
                .background(Color.White)
                .clickable(onClick = onClick)
                .clip(RoundedCornerShape(16.dp))
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
        ) {

            Image(
                modifier = Modifier
                    .size(76.dp, 54.dp)
                    .fillMaxSize(),
                contentScale = ContentScale.Fit,
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
                        .padding(start = 8.dp)
                ) {
                    Text(
                        text = item.categoriesCost,
                        maxLines = 1,
                        fontSize = 22.sp,
                        overflow = TextOverflow.Ellipsis,
                        color = Color.Black,
                        style = MaterialTheme.typography.bodyMedium
                    )

                    Text(
                        text = item.averageCost,
                        maxLines = 1,
                        fontSize = 18.sp,
                        overflow = TextOverflow.Ellipsis,
                        color = GreyLight,
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
                    color = Color.Black,
                    style = MaterialTheme.typography.headlineLarge
                )
            }
        }
    }
}