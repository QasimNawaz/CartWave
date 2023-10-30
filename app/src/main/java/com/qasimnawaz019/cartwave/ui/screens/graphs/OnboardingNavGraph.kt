package com.qasimnawaz019.cartwave.ui.screens.graphs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.qasimnawaz019.cartwave.ui.screens.onboarding.OnBoardingScreen

fun NavGraphBuilder.onBoardingNavigationGraph(navController: NavHostController) {
    navigation(
        route = Graph.ONBOARDING_GRAPH, startDestination = OnBoardingScreen.Welcome.route
    ) {
        composable(route = OnBoardingScreen.Welcome.route) {
            OnBoardingScreen(onBoardingCompleted = {
                navController.popBackStack()
                navController.navigate(route = AuthScreenInfo.Login.route)
            })
        }
    }
}

sealed class OnBoardingScreen(val route: String) {
    object Welcome : OnBoardingScreen(route = "WELCOME")
}