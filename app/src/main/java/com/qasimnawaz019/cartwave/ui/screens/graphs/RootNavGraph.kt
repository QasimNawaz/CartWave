package com.qasimnawaz019.cartwave.ui.screens.graphs

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.dialog
import com.qasimnawaz019.cartwave.R
import com.qasimnawaz019.cartwave.ui.components.AlertMessageDialog

@Composable
fun RootNavigationGraph(
    navController: NavHostController,
    startDestination: String,
    errorMessage: String?,
    onPositiveClick: () -> Unit = {},
) {
    NavHost(
        navController = navController,
        route = Graph.ROOT_GRAPH,
        startDestination = Graph.SPLASH_GRAPH
    ) {
        splashNavigationGraph(navController = navController, startDestination = startDestination)
        onBoardingNavigationGraph(navController = navController)
        authNavigationGraph(navController = navController)
        mainNavigationGraph(navController = navController)
        dialog(route = Graph.ERROR_DIALOG) {
            AlertMessageDialog(
                title = "Something went wrong !",
                message = errorMessage,
                drawable = R.drawable.ic_error_dialog,
                positiveButtonText = "Ok",
                onPositiveClick = {
                    onPositiveClick.invoke()
                },
            )
        }
    }
}

object Graph {
    const val ERROR_DIALOG = "error_dialog"
    const val ROOT_GRAPH = "root_graph"
    const val SPLASH_GRAPH = "splash_graph"
    const val ONBOARDING_GRAPH = "onboarding_graph"
    const val AUTH_GRAPH = "auth_graph"
    const val MAIN_GRAPH = "main_graph"
}