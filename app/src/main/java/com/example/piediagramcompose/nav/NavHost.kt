package com.example.piediagramcompose.nav

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.piediagramcompose.content.budget.BudgetScreen
import com.example.piediagramcompose.content.diagram.PieChartScreen

@Composable
fun MyApp() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            PieChartScreen(navController)
        }

        composable("budget") {
            BudgetScreen(navController)
        }
    }
}
