package com.example.piediagramcompose.content

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.piediagramcompose.ui.theme.OrangeLight
import com.example.piediagramcompose.ui.theme.PurpleLight




@Composable
fun SalesListItem(
    item: SalesList,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .clickable(onClick = onClick)
            .clip(RoundedCornerShape(4.dp))
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 24.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Icon(
            imageVector = Icons.Filled.Favorite,
            contentDescription = "Content",
            tint = PurpleLight,
        )

        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(
                text = item.categoriesCost,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = PurpleLight,
                style = MaterialTheme.typography.bodyMedium
            )

            Text(
                text = item.averageCost,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = OrangeLight,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}