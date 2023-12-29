package com.qasimnawaz019.cartwave.ui.screens.graphs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.qasimnawaz019.cartwave.ui.screens.main.MainScreen
import com.qasimnawaz019.cartwave.ui.screens.checkout.CheckOutScreen
import com.qasimnawaz019.cartwave.ui.screens.detail.ProductDetail
import com.qasimnawaz019.cartwave.utils.composableScaleTransition
import com.qasimnawaz019.cartwave.utils.navigateTo

fun NavGraphBuilder.mainNavigationGraph(navController: NavHostController) {
    navigation(
        route = Graph.MAIN_GRAPH, startDestination = MainScreenInfo.Main.route
    ) {
        composableScaleTransition(route = MainScreenInfo.Main.route) {
            MainScreen(onNavigate = {
                navigateTo(
                    navController = navController, destination = it, false
                )
            })
        }

        composableScaleTransition(
            route = "${MainScreenInfo.ProductDetail.route}/{PRODUCT_ID}",
            arguments = listOf(navArgument("PRODUCT_ID") { type = NavType.IntType })
        ) { backStackEntry ->
            backStackEntry.arguments?.getInt("PRODUCT_ID")?.let {
                ProductDetail(navController, it)
            }
        }

        composableScaleTransition(route = MainScreenInfo.CheckOut.route) {
            CheckOutScreen(navController)
        }
    }
}

sealed class MainScreenInfo(val route: String) {
    object Main : MainScreenInfo(route = "MAIN")

    object ProductDetail : MainScreenInfo(route = "PRODUCT_DETAIL")

    object CheckOut : MainScreenInfo(route = "CHECKOUT")
}