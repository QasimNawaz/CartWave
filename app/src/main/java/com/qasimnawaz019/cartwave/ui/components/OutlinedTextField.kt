package com.qasimnawaz019.cartwave.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.qasimnawaz019.cartwave.R

@Composable
fun CartWaveOutlinedTextField(
    modifier: Modifier,
    testTag: String = "",
    errorTestTag: String = "",
    value: String,
    isError: Boolean = false,
    errorMessage: String? = "",
    onValueChange: ((String) -> Unit),
    label: String,
    keyboardType: KeyboardType = KeyboardType.Text,
    iconDrawable: Int?
) {
    Column {
        OutlinedTextField(
            modifier = modifier.testTag(testTag),
            value = value,
            onValueChange = { onValueChange(it) },
            label = { Text(text = label) },
            isError = isError,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            trailingIcon = {
                iconDrawable?.let {
                    Icon(
                        painter = painterResource(id = it),
                        contentDescription = "null",
                    )
                }
            },
            singleLine = true,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
                textColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
                focusedLabelColor = MaterialTheme.colorScheme.primary,
                unfocusedLabelColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
                cursorColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
                trailingIconColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
            )
        )
        if (isError) {
            ErrorText(
                modifier = Modifier
                    .padding(top = 4.dp, start = 4.dp)
                    .testTag(errorTestTag),
                errorMessage
            )
        } else {
            Spacer(modifier = Modifier.size(20.dp))
        }
    }
}

@Composable
fun CartWaveOutlinedTextFieldPassword(
    modifier: Modifier,
    testTag: String = "",
    errorTestTag: String = "",
    value: String,
    isError: Boolean = false,
    errorMessage: String? = "",
    onValueChange: ((String) -> Unit),
    label: String,
) {
    var showPassword by remember { mutableStateOf(value = false) }
    Column {
        OutlinedTextField(
            modifier = modifier.testTag(testTag),
            value = value,
            onValueChange = { onValueChange(it) },
            label = { Text(text = label) },
            isError = isError,
            visualTransformation = if (showPassword) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                if (showPassword) {
                    IconButton(onClick = { showPassword = false }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_eye),
                            contentDescription = "hide_password",
                        )
                    }
                } else {
                    IconButton(onClick = { showPassword = true }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_eye_slash),
                            contentDescription = "hide_password",
                        )
                    }
                }
            },
            singleLine = true,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
                textColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
                focusedLabelColor = MaterialTheme.colorScheme.primary,
                unfocusedLabelColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
                cursorColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
                trailingIconColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
            )
        )
        if (isError) {
            ErrorText(
                modifier = Modifier
                    .testTag(errorTestTag)
                    .padding(top = 4.dp, start = 4.dp),
                errorMessage
            )
        } else {
            Spacer(modifier = Modifier.size(20.dp))
        }
    }
}

@Composable
fun ErrorText(modifier: Modifier, errorMessage: String?) {
    Text(
        modifier = modifier,
        text = errorMessage ?: "",
        color = MaterialTheme.colorScheme.error,
        style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.error)
    )
}