package com.qasimnawaz019.cartwave.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.qasimnawaz019.cartwave.R

@Composable
fun BottomBar(
    navController: NavHostController,
) {
    val screens = listOf(
        BottomBarScreenInfo.Home,
        BottomBarScreenInfo.Wishlist,
        BottomBarScreenInfo.Cart,
        BottomBarScreenInfo.Profile,
    )

    val navStackBackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navStackBackEntry?.destination
    val bottomBarDestination = screens.any { it.route == currentDestination?.route }
    val shape = RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp)
    if (bottomBarDestination) {
        CartWaveSurface(
            color = MaterialTheme.colorScheme.surface,
            modifier = Modifier
                .padding(top = 6.dp),
//                .border(
//                    width = 1.dp,
//                    color = MaterialTheme.colorScheme.primary,
//                    shape = shape
//                ),
            elevation = 4.dp,
            shape = shape
        ) {
            Row(
                modifier = Modifier
                    .height(BottomNavHeight)
                    .fillMaxWidth()
                    .navigationBarsPadding(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                screens.forEach { screen ->
                    AddItem(
                        screen = screen,
                        currentDestination = currentDestination,
                        navController = navController
                    )
                }
            }
        }
    }
}

@Composable
fun AddItem(
    screen: BottomBarScreenInfo,
    currentDestination: NavDestination?,
    navController: NavHostController
) {
    val interactionSource = remember { MutableInteractionSource() }
    val selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true
    Box(
        modifier = Modifier
            .height(40.dp)
            .clickable(
                interactionSource = interactionSource, indication = null
            ) {
                navController.navigate(screen.route) {
                    popUpTo(navController.graph.findStartDestination().id)
                    launchSingleTop = true
                }
            }, contentAlignment = Alignment.CenterStart
    ) {
        Row {
            Spacer(modifier = Modifier.width(15.dp))
            AnimatedVisibility(
                visible = selected,
            ) {
                Text(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(
                            color = if (selected) MaterialTheme.colorScheme.primary.copy(alpha = 0.5f) else Color.Transparent,
                        )
                        .padding(
                            start = 30.dp, end = 10.dp, top = 8.dp, bottom = 8.dp
                        ), text = screen.title, color = Color.White, fontSize = 11.sp
                )
            }
        }
        Icon(
            modifier = Modifier
                .width(40.dp)
                .height(40.dp)
                .clip(CircleShape)
                .background(color = if (selected) MaterialTheme.colorScheme.primary else Color.Transparent)
                .padding(10.dp),
            painter = painterResource(id = screen.icon),
            contentDescription = "icon",
            tint = if (selected) Color.White else MaterialTheme.colorScheme.primary,
        )
    }
//    Box(
//        modifier = Modifier
//            .height(30.dp)
//            .clip(CircleShape)
//            .background(
//                color = if (selected) MaterialTheme.colorScheme.primary.copy(alpha = 0.5f) else Color.Transparent,
//            )
//            .clickable(onClick = {
//                navController.navigate(screen.route) {
//                    popUpTo(navController.graph.findStartDestination().id)
//                    launchSingleTop = true
//                }
//            }), contentAlignment = Alignment.Center
//    ) {
//        Row(
//            verticalAlignment = Alignment.CenterVertically,
//            horizontalArrangement = Arrangement.spacedBy(4.dp)
//        ) {
//            Icon(
//                modifier = Modifier
//                    .width(30.dp)
//                    .height(30.dp)
//                    .clip(CircleShape)
//                    .background(color = if (selected) MaterialTheme.colorScheme.primary else Color.Transparent)
//                    .padding(5.dp),
//                painter = painterResource(id = screen.icon),
//                contentDescription = "icon",
//                tint = if (selected) Color.White else MaterialTheme.colorScheme.primary,
//            )
//            AnimatedVisibility(visible = selected) {
//                Text(
//                    modifier = Modifier.padding(
//                        start = 5.dp,
//                        end = 10.dp,
//                        top = 8.dp,
//                        bottom = 8.dp
//                    ),
//                    text = screen.title, color = Color.White, fontSize = 11.sp
//                )
//            }
//        }
//    }
}

val BottomNavHeight = 52.dp

sealed class BottomBarScreenInfo(
    val route: String, val title: String, val icon: Int
) {
    object Home : BottomBarScreenInfo(
        route = "home", title = "Home", icon = R.drawable.ic_home
    )

    object Wishlist : BottomBarScreenInfo(
        route = "wishlist", title = "Wishlist", icon = R.drawable.ic_favourite
    )

    object Cart : BottomBarScreenInfo(
        route = "cart", title = "Cart", icon = R.drawable.ic_cart
    )

    object Profile : BottomBarScreenInfo(
        route = "profile", title = "Profile", icon = R.drawable.ic_profile
    )
}