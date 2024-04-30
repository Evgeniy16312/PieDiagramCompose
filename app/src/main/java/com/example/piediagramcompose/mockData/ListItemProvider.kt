package com.example.piediagramcompose.mockData

import com.example.piediagramcompose.content.SalesList

fun populateList(value: Int): List<SalesList> {
    return listOf(
        SalesList("entertainment", "last month: $value$", "${value + 45}$"),
        SalesList("sport", "last month: $value$", "${value + 125}$"),
        SalesList("car", "last month: $value$", "${value - 45}$"),
        SalesList("market", "last month: $value$", "${value + 145}$"),
        SalesList("trips", "last month: $value$", "${value + 450}$"),
    )
}

