package com.qasimnawaz019.cartwave.ui.screens.graphs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.qasimnawaz019.cartwave.ui.screens.auth.ForgotPasswordScreen
import com.qasimnawaz019.cartwave.ui.screens.auth.login.LoginScreen
import com.qasimnawaz019.cartwave.ui.screens.auth.SignUpScreen
import com.qasimnawaz019.cartwave.ui.screens.auth.VerificationCodeScreen

fun NavGraphBuilder.authNavigationGraph(
    navController: NavHostController
) {
    navigation(
        route = Graph.AUTH_GRAPH, startDestination = AuthScreenInfo.Login.route
    ) {
        composable(route = AuthScreenInfo.Login.route) {
            LoginScreen(navController)
        }
        composable(route = AuthScreenInfo.SignUp.route) {
            SignUpScreen(navController)
        }
        composable(route = AuthScreenInfo.VerificationCode.route) {
            VerificationCodeScreen(navController)
        }
        composable(route = AuthScreenInfo.ForgotPassword.route) {
            ForgotPasswordScreen(navController)
        }
    }
}

sealed class AuthScreenInfo(val route: String) {
    object Login : SplashScreenInfo(route = "LOGIN")
    object SignUp : SplashScreenInfo(route = "SIGNUP")
    object VerificationCode : SplashScreenInfo(route = "VERIFICATION_CODE")
    object ForgotPassword : SplashScreenInfo(route = "FORGOT_PASSWORD")
}