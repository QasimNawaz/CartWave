package com.qasimnawaz019.cartwave.ui.screens.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.qasimnawaz019.cartwave.R
import com.qasimnawaz019.cartwave.ui.components.OtpView

@Composable
fun VerificationCodeScreen(navController: NavHostController) {

    var isNextBtnStatus by remember { mutableStateOf(value = false) }
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        IconButton(
            modifier = Modifier.padding(start = 10.dp, top = 20.dp),
            onClick = { navController.popBackStack() }) {
            Icon(
                modifier = Modifier.size(20.dp),
                painter = painterResource(id = R.drawable.ic_arrow_left),
                contentDescription = "null",
                tint = MaterialTheme.colorScheme.onBackground
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f, true),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Spacer(modifier = Modifier.size(10.dp))
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        modifier = Modifier.size(150.dp),
                        painter = painterResource(id = R.drawable.ic_otp),
                        contentDescription = "OTP Image"
                    )
                    Spacer(modifier = Modifier.size(30.dp))
                    Text(
                        text = "OTP Verification",
                        fontSize = MaterialTheme.typography.headlineSmall.fontSize,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Spacer(modifier = Modifier.size(10.dp))
                    Text(
                        modifier = Modifier.padding(start = 30.dp, end = 30.dp),
                        text = "Access the OTP we've emailed you, and enter it here to prove your identity and access your features",
                        fontSize = MaterialTheme.typography.bodySmall.fontSize,
                        fontWeight = FontWeight.Light,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.size(20.dp))
                    OtpView(smsCodeLength = 5,
                        textStyle = MaterialTheme.typography.headlineSmall,
                        smsFulled = {
                            isNextBtnStatus = it.length == 5
                        })
//                Spacer(modifier = Modifier.size(20.dp))
                }
            }
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f, false)
                    .padding(start = 30.dp, end = 30.dp, bottom = 30.dp),
                onClick = {
                    navController.popBackStack()
                    navController.popBackStack()
                }, colors = ButtonDefaults.buttonColors(
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                    backgroundColor = MaterialTheme.colorScheme.primary,
                    disabledContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    disabledBackgroundColor = MaterialTheme.colorScheme.surfaceVariant
                ), enabled = isNextBtnStatus
            ) {
                Text(text = "Verify")
            }
        }
    }
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(30.dp),
//        verticalArrangement = Arrangement.SpaceBetween
//    ) {
//        Column {
//
//            Spacer(modifier = Modifier.size(10.dp))
//            Column(
//                modifier = Modifier
//                    .fillMaxWidth(),
//                verticalArrangement = Arrangement.Top,
//                horizontalAlignment = Alignment.CenterHorizontally
//            ) {
//                Image(
//                    modifier = Modifier.size(150.dp),
//                    painter = painterResource(id = R.drawable.ic_otp),
//                    contentDescription = "OTP Image"
//                )
//                Spacer(modifier = Modifier.size(30.dp))
//                Text(
//                    text = "OTP Verification",
//                    fontSize = MaterialTheme.typography.headlineSmall.fontSize,
//                    fontWeight = FontWeight.Bold,
//                    color = MaterialTheme.colorScheme.onBackground
//                )
//                Spacer(modifier = Modifier.size(10.dp))
//                Text(
//                    text = "Access the OTP we've emailed you, and enter it here to prove your identity and access your features",
//                    fontSize = MaterialTheme.typography.bodySmall.fontSize,
//                    fontWeight = FontWeight.Light,
//                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
//                    textAlign = TextAlign.Center
//                )
//                Spacer(modifier = Modifier.size(20.dp))
//                OtpView(smsCodeLength = 5,
//                    textStyle = MaterialTheme.typography.headlineSmall,
//                    smsFulled = {
//                        isNextBtnStatus = it.length == 5
//                    })
////                Spacer(modifier = Modifier.size(20.dp))
//            }
//        }
//
//
//    }
}