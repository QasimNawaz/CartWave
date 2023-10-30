package com.qasimnawaz019.cartwave.ui.screens.graphs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.qasimnawaz019.cartwave.ui.screens.MainScreen

fun NavGraphBuilder.mainNavigationGraph(navController: NavHostController) {
    navigation(
        route = Graph.MAIN_GRAPH, startDestination = MainScreenInfo.Main.route
    ) {
        composable(route = MainScreenInfo.Main.route) {
            MainScreen()
        }
    }
}

sealed class MainScreenInfo(val route: String) {
    object Main : MainScreenInfo(route = "MAIN")
}