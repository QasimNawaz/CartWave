package com.qasimnawaz019.cartwave.ui.screens.graphs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.navigation
import com.qasimnawaz019.cartwave.ui.screens.auth.ForgotPasswordScreen
import com.qasimnawaz019.cartwave.ui.screens.auth.VerificationCodeScreen
import com.qasimnawaz019.cartwave.ui.screens.auth.login.LoginScreen
import com.qasimnawaz019.cartwave.ui.screens.auth.register.RegisterScreen
import com.qasimnawaz019.cartwave.utils.composableSlideTransition

fun NavGraphBuilder.authNavigationGraph(
    navController: NavHostController
) {
    navigation(
        route = Graph.AUTH_GRAPH, startDestination = AuthScreenInfo.Login.route
    ) {
        composableSlideTransition(route = AuthScreenInfo.Login.route) {
            LoginScreen(navController)
        }
        composableSlideTransition(route = AuthScreenInfo.SignUp.route) {
            RegisterScreen(navController)
        }
        composableSlideTransition(route = AuthScreenInfo.VerificationCode.route) {
            VerificationCodeScreen(navController)
        }
        composableSlideTransition(route = AuthScreenInfo.ForgotPassword.route) {
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