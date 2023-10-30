package com.qasimnawaz019.cartwave

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performTextClearance
import androidx.compose.ui.test.performTextInput
import androidx.navigation.compose.rememberNavController
import com.qasimnawaz019.cartwave.ui.screens.auth.login.LoginScreen
import org.junit.Rule
import org.junit.Test

class LoginScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testEmptyUsernameFieldError() {
        composeTestRule.setContent {
            val navController = rememberNavController()
            LoginScreen(navController = navController)
        }
        composeTestRule.onNodeWithTag("UsernameField").performTextClearance()

        composeTestRule.onNodeWithTag("UsernameFieldError").assertTextEquals(
            "Username field is required."
        )
    }

    @Test
    fun testInvalidUsernameFieldError() {
        composeTestRule.setContent {
            val navController = rememberNavController()
            LoginScreen(navController = navController)
        }
        composeTestRule.onNodeWithTag("UsernameField").performTextClearance()
        composeTestRule.onNodeWithTag("UsernameField").performTextInput("ab")

        composeTestRule.onNodeWithTag("UsernameFieldError").assertTextEquals(
            "Username should be at least 3 characters."
        )
    }

    @Test
    fun testEmptyPasswordFieldError() {
        composeTestRule.setContent {
            val navController = rememberNavController()
            LoginScreen(navController = navController)
        }
        composeTestRule.onNodeWithTag("PasswordField").performTextClearance()

        composeTestRule.onNodeWithTag("PasswordFieldError").assertTextEquals(
            "Password field is required."
        )

    }

    @Test
    fun testInvalidPasswordFieldError() {
        composeTestRule.setContent {
            val navController = rememberNavController()
            LoginScreen(navController = navController)
        }
        composeTestRule.onNodeWithTag("PasswordField").performTextClearance()
        composeTestRule.onNodeWithTag("PasswordField").performTextInput("12")

        // Check if the error text displayed
        composeTestRule.onNodeWithTag("PasswordFieldError").assertTextEquals(
            "Password should be at least 6 characters."
        )

    }

    @Test
    fun testLoginButtonEnabled() {
        composeTestRule.setContent {
            val navController = rememberNavController()
            LoginScreen(navController = navController)
        }

        composeTestRule.onNodeWithTag("UsernameField").performTextClearance()
        composeTestRule.onNodeWithTag("PasswordField").performTextClearance()

        composeTestRule.onNodeWithTag("UsernameField").performTextInput("mor_2314")
        composeTestRule.onNodeWithTag("PasswordField").performTextInput("83r5^_")

        // Check if the login button is enabled
        composeTestRule.onNodeWithTag("LoginButton").assertIsEnabled()
    }

    @Test
    fun testLoginButtonDisabled() {
        composeTestRule.setContent {
            val navController = rememberNavController()
            LoginScreen(navController = navController)
        }

        composeTestRule.onNodeWithTag("UsernameField").performTextClearance()
        composeTestRule.onNodeWithTag("PasswordField").performTextClearance()

        composeTestRule.onNodeWithTag("UsernameField").performTextInput("ab")
        composeTestRule.onNodeWithTag("PasswordField").performTextInput("12")

        // Check if the login button is disabled
        composeTestRule.onNodeWithTag("LoginButton").assertIsNotEnabled()
    }
}