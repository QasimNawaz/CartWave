package com.qasimnawaz019.cartwave.ui.screens.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.qasimnawaz019.cartwave.R
import com.qasimnawaz019.cartwave.ui.components.CartWaveOutlinedTextField

@Composable
fun ForgotPasswordScreen(navController: NavHostController) {
    var userName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isChecked by remember { mutableStateOf(value = false) }
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        IconButton(
            modifier = Modifier.padding(start = 10.dp, top = 20.dp),
            onClick = { navController.popBackStack() }) {
            Icon(
                modifier = Modifier
                    .size(20.dp),
                painter = painterResource(id = R.drawable.ic_arrow_left),
                contentDescription = "null",
                tint = MaterialTheme.colorScheme.onBackground
            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(30.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Spacer(modifier = Modifier.size(20.dp))
                Text(
                    text = "Forgot\nPassword?",
                    fontSize = MaterialTheme.typography.headlineSmall.fontSize,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.size(10.dp))

                Text(
                    text = "Don’t worry! it happens. Please enter the\n" +
                            "address associated with your account.",
                    fontSize = MaterialTheme.typography.bodySmall.fontSize,
                    fontWeight = FontWeight.Light,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)
                )
                Spacer(modifier = Modifier.size(20.dp))
                CartWaveOutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = userName,
                    onValueChange = { userName = it },
                    label = "Email ID / Mobile number",
                    iconDrawable = null
                )
            }

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f, false),
                onClick = {
                    navController.popBackStack()
                },
                colors = ButtonDefaults.buttonColors(
                    contentColor = Color.White, backgroundColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text(text = "Submit")
            }
        }
    }
}