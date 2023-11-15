package com.qasimnawaz019.cartwave.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.qasimnawaz019.cartwave.ui.components.BottomBar
import com.qasimnawaz019.cartwave.ui.components.BottomBarScreenInfo
import com.qasimnawaz019.cartwave.ui.components.CartWaveScaffold
import com.qasimnawaz019.cartwave.ui.screens.graphs.HomeNavigationGraph

@Composable
fun MainScreen(
    onNavigate: (route: String) -> Unit,
    navController: NavHostController = rememberNavController(),
) {
    CartWaveScaffold(bottomBar = { BottomBar(navController = navController) }) { innerPadding ->
        HomeNavigationGraph(
            navController = navController,
            startDestination = BottomBarScreenInfo.Home.route,
            innerPadding = innerPadding,
            onNavigate = onNavigate
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MainScreen({})
}