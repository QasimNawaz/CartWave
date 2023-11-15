package com.qasimnawaz019.cartwave.ui.screens.graphs

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.qasimnawaz019.cartwave.ui.components.BottomBarScreenInfo
import com.qasimnawaz019.cartwave.ui.screens.cart.CartScreen
import com.qasimnawaz019.cartwave.ui.screens.home.HomeScreen
import com.qasimnawaz019.cartwave.ui.screens.profile.ProfileScreen
import com.qasimnawaz019.cartwave.ui.screens.wishlist.WishlistScreen
import com.qasimnawaz019.cartwave.utils.composableScaleTransition
import com.qasimnawaz019.cartwave.utils.composableSlideTransition

@Composable
fun HomeNavigationGraph(
    navController: NavHostController,
    startDestination: String,
    innerPadding: PaddingValues,
    onNavigate: (route: String) -> Unit,
) {
    NavHost(
        navController = navController,
        route = Graph.MAIN_GRAPH,
        startDestination = startDestination,
        modifier = Modifier.padding(innerPadding)
    ) {

        composableScaleTransition(route = BottomBarScreenInfo.Home.route) {
            HomeScreen(onNavigate)
        }

        composableScaleTransition(route = BottomBarScreenInfo.Wishlist.route) {
            WishlistScreen()
        }

        composableScaleTransition(route = BottomBarScreenInfo.Cart.route) {
            CartScreen()
        }

        composableScaleTransition(route = BottomBarScreenInfo.Profile.route) {
            ProfileScreen()
        }
    }
}