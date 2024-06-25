package com.example.piediagramcompose.mockData

import com.example.piediagramcompose.content.SalesList

fun populateList(value: Int): List<SalesList> {
    return listOf(
        SalesList("entertainment", "last month: ${value} Pуб.", "${value + 45} Pуб."),
        SalesList("sport", "last month: ${value} Pуб.", "${value + 125} Pуб."),
        SalesList("car", "last month: ${value} Pуб.", "${value - 45} Pуб."),
        SalesList("market", "last month: ${value} Pуб.", "${value + 145} Pуб."),
        SalesList("trips", "last month: ${value} Pуб.", "${value + 450} Pуб."),
    )
}

