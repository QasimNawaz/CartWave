package com.qasimnawaz019.cartwave.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlertMessageDialog(
    title: String,
    message: String? = null,
    @DrawableRes drawable: Int? = null,
    positiveButtonText: String? = null,
    negativeButtonText: String? = null,
    onPositiveClick: () -> Unit = {},
    onNegativeClick: () -> Unit = {},
    onDismiss: () -> Unit = {}
) {

    AlertDialog(
        onDismissRequest = {}, properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false,
        )
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            shape = RoundedCornerShape(size = 12.dp)
        ) {
            Column(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.secondaryContainer)
                    .padding(all = 16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                drawable?.let {
                    Image(
                        modifier = Modifier.size(100.dp),
                        painter = painterResource(id = it),
                        contentDescription = null
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = title,
                    fontSize = MaterialTheme.typography.titleMedium.fontSize,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(10.dp))
                message?.let {
                    Text(
                        text = it,
                        fontSize = MaterialTheme.typography.labelLarge.fontSize,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
                        textAlign = TextAlign.Center
                    )
                }
                Spacer(modifier = Modifier.height(15.dp))
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 16.dp, start = 16.dp)
                ) {
                    negativeButtonText?.let {
                        Button(
                            modifier = Modifier.weight(1f), onClick = {
                                onNegativeClick()
                                onDismiss()
                            }, colors = ButtonDefaults.buttonColors(
                                contentColor = Color.White,
                                backgroundColor = MaterialTheme.colorScheme.primary
                            )
                        ) {
                            Text(text = it)
                        }

                        Spacer(modifier = Modifier.width(6.dp))
                    }
                    positiveButtonText?.let {
                        Button(
                            modifier = Modifier.weight(1f), onClick = {
                                onPositiveClick()
                                onDismiss()
                            }, colors = ButtonDefaults.buttonColors(
                                contentColor = Color.White,
                                backgroundColor = MaterialTheme.colorScheme.primary
                            )
                        ) {
                            Text(text = it)
                        }
                    }
                }
            }

        }

    }
}