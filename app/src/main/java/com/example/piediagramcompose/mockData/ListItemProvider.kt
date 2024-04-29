package com.example.piediagramcompose.mockData

import com.example.piediagramcompose.content.SalesList

fun populateList(): List<SalesList> {
    return listOf(
        SalesList("cinema", "low 23$", "43$"),
        SalesList("sport", "low 76$", "673$"),
        SalesList("media", "medium 597$", "2234$"),
        SalesList("market", "low 77$", "467$"),
        SalesList("oil", "height 1111$", "6554$"),
    )
}
