package com.qasimnawaz019.cartwave.ui.screens.auth.login

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.qasimnawaz019.cartwave.R
import com.qasimnawaz019.cartwave.ui.components.AlertMessageDialog
import com.qasimnawaz019.cartwave.ui.components.CartWaveLabelledCheckBox
import com.qasimnawaz019.cartwave.ui.components.CartWaveOutlinedTextField
import com.qasimnawaz019.cartwave.ui.components.CartWaveOutlinedTextFieldPassword
import com.qasimnawaz019.cartwave.ui.components.LoadingDialog
import com.qasimnawaz019.cartwave.ui.components.SocialButton
import com.qasimnawaz019.cartwave.ui.screens.graphs.AuthScreenInfo
import com.qasimnawaz019.cartwave.ui.screens.graphs.Graph
import com.qasimnawaz019.cartwave.utils.navigateTo
import com.qasimnawaz019.domain.dto.login.LoginRequestDto
import com.qasimnawaz019.domain.model.User
import com.qasimnawaz019.domain.utils.NetworkUiState
import org.koin.androidx.compose.getViewModel

@Composable
fun LoginScreen(
    navController: NavHostController, viewModel: LoginScreenViewModel = getViewModel()
) {
    var isChecked by remember { mutableStateOf(value = false) }
    val loginState: NetworkUiState<User> by viewModel.networkUiState.collectAsStateWithLifecycle()
    val redirectToHome: Boolean by viewModel.redirectToHome.collectAsStateWithLifecycle()
    LaunchedEffect(key1 = redirectToHome, block = {
        if (redirectToHome) {
            navigateTo(
                navController = navController, destination = Graph.MAIN_GRAPH, true
            )
        }
    })
    when (loginState) {
        is NetworkUiState.Loading -> {
            LoadingDialog()
        }

        is NetworkUiState.Error -> {
            AlertMessageDialog(title = "Something went wrong !",
                message = (loginState as NetworkUiState.Error).error,
                drawable = R.drawable.ic_error_dialog,
                positiveButtonText = "Retry",
                negativeButtonText = "Cancel",
                onPositiveClick = {
                    viewModel.performLogin(
                        LoginRequestDto(
                            email = viewModel.emailState.text,
                            password = viewModel.passwordState.text
                        )
                    )
                },
                onNegativeClick = { viewModel.emptyUiState() })
        }

        is NetworkUiState.Success -> {
            (loginState as NetworkUiState.Success<User>).data.let {
                viewModel.saveUser(it, isChecked)
            }
        }

        else -> {}
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
            .padding(30.dp),
    ) {
        Spacer(modifier = Modifier.size(20.dp))
        Text(
            text = "Hello",
            fontSize = MaterialTheme.typography.headlineMedium.fontSize,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.size(10.dp))
        Text(
            text = "Again!",
            fontSize = MaterialTheme.typography.headlineSmall.fontSize,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.size(10.dp))
        Text(
            text = "Welcome back you’ve\n" + "been missed",
            fontSize = MaterialTheme.typography.bodySmall.fontSize,
            fontWeight = FontWeight.Light,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)
        )
        Spacer(modifier = Modifier.size(20.dp))
        CartWaveOutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            testTag = "UsernameField",
            errorTestTag = "UsernameFieldError",
            value = viewModel.emailState.text,
            isError = viewModel.emailState.error != null,
            errorMessage = viewModel.emailState.error,
            onValueChange = {
                viewModel.onEmailChange(it)
                viewModel.emailState.validate()
            },
            label = "Email",
            keyboardType = KeyboardType.Email,
            iconDrawable = R.drawable.ic_email
        )
        Spacer(modifier = Modifier.size(5.dp))
        CartWaveOutlinedTextFieldPassword(
            modifier = Modifier.fillMaxWidth(),
            testTag = "PasswordField",
            errorTestTag = "PasswordFieldError",
            value = viewModel.passwordState.text,
            isError = viewModel.passwordState.error != null,
            errorMessage = viewModel.passwordState.error,
            onValueChange = {
                viewModel.onPasswordChange(it)
                viewModel.passwordState.validate()
            },
            label = "Password"
        )

        Spacer(modifier = Modifier.size(5.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()
        ) {
            CartWaveLabelledCheckBox(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                isChecked = isChecked,
                onCheckedChange = { isChecked = it },
                label = "Remember me"
            )

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .clickable {
                        navController.navigate(AuthScreenInfo.ForgotPassword.route)
                    },
                text = "Forgot the password?",
                textAlign = TextAlign.End,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.primary,
            )
        }
        Spacer(modifier = Modifier.size(20.dp))
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .testTag("LoginButton"),
            onClick = {
                viewModel.performLogin(
                    LoginRequestDto(
                        email = viewModel.emailState.text, password = viewModel.passwordState.text
                    )
                )
            },
            colors = ButtonDefaults.buttonColors(
                contentColor = MaterialTheme.colorScheme.onPrimary,
                backgroundColor = MaterialTheme.colorScheme.primary,
                disabledContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
                disabledBackgroundColor = MaterialTheme.colorScheme.surfaceVariant
            ),
            enabled = viewModel.ifAllDataValid()
        ) {
            Text(text = "Login")
        }
        Spacer(modifier = Modifier.size(10.dp))
        Text(
            modifier = Modifier.align(alignment = Alignment.CenterHorizontally),
            text = "or continue with",
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
            fontSize = 12.sp
        )
        Spacer(modifier = Modifier.size(10.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()
        ) {
            SocialButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                drawable = R.drawable.ic_google,
                label = "Google"
            )
            Spacer(modifier = Modifier.size(10.dp))
            SocialButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                drawable = R.drawable.ic_facebook,
                label = "Facebook"
            )
        }
        Spacer(modifier = Modifier.size(10.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .align(alignment = Alignment.CenterHorizontally)
                .wrapContentWidth()
        ) {
            Text(
                text = "don't have an account ? ",
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
                fontSize = 12.sp
            )
            Text(
                modifier = Modifier.clickable {
                    navController.navigate(AuthScreenInfo.SignUp.route)
                }, text = "Sign Up", color = MaterialTheme.colorScheme.primary, fontSize = 12.sp
            )
        }
    }

}
