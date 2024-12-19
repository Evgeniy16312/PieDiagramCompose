package com.example.piediagramcompose.mockData

import com.example.piediagramcompose.model.SalesList

fun populateList(
    valueOne: Int,
    valueTwo: Int,
    valueThree: Int,
    valueFour: Int,
    valueFive: Int
                 ): List<SalesList> {
    return listOf(
        SalesList("развлечения", "прошлый месяц: ${valueOne} Pуб.", "${valueOne + 85} Pуб."),
        SalesList("спорт", "прошлый месяц: ${valueTwo} Pуб.", "${valueTwo + 125} Pуб."),
        SalesList("авто", "прошлый месяц: ${valueThree} Pуб.", "${valueThree + 245} Pуб."),
        SalesList("маркет", "прошлый месяц: ${valueFour} Pуб.", "${valueFour + 145} Pуб."),
        SalesList("путешествия", "прошлый месяц: ${valueFive} Pуб.", "${valueFive + 450} Pуб."),
    )
}

