package com.qasimnawaz019.cartwave.utils

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.runtime.Composable
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

fun navigateTo(navController: NavController, destination: String, clearBackStack: Boolean = false) {
    navController.navigate(destination) {
        if (clearBackStack) popUpTo(navController.graph.id) {
            inclusive = true
        }
    }
}

fun NavGraphBuilder.composableSlideTransition(
    route: String,
    arguments: List<NamedNavArgument> = emptyList(),
    deepLinks: List<NavDeepLink> = emptyList(),
    content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit
) {
    composable(
        route,
        arguments,
        deepLinks,
        enterTransition = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Companion.Left,
                animationSpec = tween(220)
            )
        },
        exitTransition = {
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Companion.Left,
                animationSpec = tween(220)
            )
        },
        popEnterTransition = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Companion.Right,
                animationSpec = tween(220)
            )
        },
        popExitTransition = {
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Companion.Right,
                animationSpec = tween(220)
            )
        },
        content
    )
}

fun NavGraphBuilder.composableScaleTransition(
    route: String,
    arguments: List<NamedNavArgument> = emptyList(),
    deepLinks: List<NavDeepLink> = emptyList(),
    content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit
) {
    composable(
        route,
        arguments,
        deepLinks,
        enterTransition = {
            scaleIntoContainer()
        }, exitTransition = {
            scaleOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Companion.Left)
        }, popEnterTransition = {
            scaleIntoContainer(AnimatedContentTransitionScope.SlideDirection.Companion.Right)
        }, popExitTransition = {
            scaleOutOfContainer()
        },
        content
    )
}

fun scaleIntoContainer(
    direction: AnimatedContentTransitionScope.SlideDirection = AnimatedContentTransitionScope.SlideDirection.Companion.Left,
    initialScale: Float = if (direction == AnimatedContentTransitionScope.SlideDirection.Companion.Right) 0.9f else 1.1f
): EnterTransition {
    return scaleIn(
        animationSpec = tween(220),
        initialScale = initialScale
    ) + fadeIn(animationSpec = tween(220))
}

fun scaleOutOfContainer(
    direction: AnimatedContentTransitionScope.SlideDirection = AnimatedContentTransitionScope.SlideDirection.Companion.Right,
    targetScale: Float = if (direction == AnimatedContentTransitionScope.SlideDirection.Companion.Left) 0.9f else 1.1f
): ExitTransition {
    return scaleOut(
        animationSpec = tween(
            durationMillis = 220
        ), targetScale = targetScale
    ) + fadeOut(tween(delayMillis = 90))
}