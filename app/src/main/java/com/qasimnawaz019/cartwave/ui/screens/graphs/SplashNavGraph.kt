package com.qasimnawaz019.cartwave.ui.screens.graphs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.qasimnawaz019.cartwave.ui.screens.splash.SplashScreen

fun NavGraphBuilder.splashNavigationGraph(
    navController: NavHostController, startDestination: String
) {
    navigation(
        route = Graph.SPLASH_GRAPH, startDestination = SplashScreenInfo.Splash.route
    ) {
        composable(route = SplashScreenInfo.Splash.route) {
            SplashScreen(navController = navController, startDestination = startDestination)
        }
    }
}

sealed class SplashScreenInfo(val route: String) {
    object Splash : SplashScreenInfo(route = "SPLASH")
}