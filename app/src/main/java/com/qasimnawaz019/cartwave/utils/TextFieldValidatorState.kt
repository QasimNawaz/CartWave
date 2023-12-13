package com.qasimnawaz019.cartwave.utils

import android.util.Patterns

class NameState :
    TextFieldState(
        validator = ::isNameValid,
        errorMessage = ::nameErrorMessage,
        defaultText = ""
    )

class EmailState :
    TextFieldState(
        validator = ::isEmailValid,
        errorMessage = ::emailErrorMessage,
        defaultText = ""
    )

class PasswordState :
    TextFieldState(
        validator = ::isPasswordValid,
        errorMessage = ::passwordErrorMessage,
        defaultText = ""
    )

private fun isNameValid(name: String): Boolean =
    name.length >= 3

private fun nameErrorMessage(password: String) =
    if (password.isEmpty()) "Field is required." else "Name be at least 3 characters."


private fun isPasswordValid(password: String): Boolean =
    password.length >= 4

private fun passwordErrorMessage(password: String) =
    if (password.isEmpty()) "Password field is required." else "Password should be at least 4 characters."

private fun isEmailValid(email: String): Boolean =
    Patterns.EMAIL_ADDRESS.matcher(email).matches()

private fun emailErrorMessage(email: String) =
    if (email.isEmpty()) "Email field is required." else "Invalid email"
