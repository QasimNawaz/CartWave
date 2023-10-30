package com.qasimnawaz019.cartwave.ui.screens.graphs

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.qasimnawaz019.cartwave.ui.components.BottomBarScreenInfo
import com.qasimnawaz019.cartwave.ui.screens.cart.CartScreen
import com.qasimnawaz019.cartwave.ui.screens.home.HomeScreen
import com.qasimnawaz019.cartwave.ui.screens.profile.ProfileScreen
import com.qasimnawaz019.cartwave.ui.screens.wishlist.WishlistScreen

@Composable
fun HomeNavigationGraph(
    navController: NavHostController,
    startDestination: String,
    innerPadding: PaddingValues,
) {
    NavHost(
        navController = navController,
        route = Graph.MAIN_GRAPH,
        startDestination = startDestination,
        modifier = Modifier.padding(innerPadding)
    ) {

        composable(route = BottomBarScreenInfo.Home.route) {
            HomeScreen()
        }

        composable(route = BottomBarScreenInfo.Wishlist.route) {
            WishlistScreen()
        }

        composable(route = BottomBarScreenInfo.Cart.route) {
            CartScreen()
        }

        composable(route = BottomBarScreenInfo.Profile.route) {
            ProfileScreen()
        }
    }
}