package com.qasimnawaz019.cartwave.ui.components

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun OtpView(
    smsCodeLength: Int,
    textStyle: TextStyle,
    smsFulled: (String) -> Unit
) {
    val focusRequesters: List<FocusRequester> = remember {
        (0 until smsCodeLength).map { FocusRequester() }
    }
    val enteredNumbers = remember {
        mutableStateListOf(
            *((0 until smsCodeLength).map { "" }.toTypedArray())
        )
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        for (index in 0 until smsCodeLength) {
            OutlinedTextField(
                modifier = Modifier
                    .width(46.dp)
                    .height(60.dp)
                    .focusRequester(focusRequesters[index])
                    .focusRequester(focusRequester = focusRequesters[index])
                    .onKeyEvent { keyEvent: KeyEvent ->
                        val currentValue = enteredNumbers.getOrNull(index) ?: ""
                        if (keyEvent.key == Key.Backspace) {
                            if (currentValue.isNotEmpty()) {
                                enteredNumbers[index] = ""
                                smsFulled.invoke(enteredNumbers.joinToString(separator = ""))
                                focusRequesters
                                    .getOrNull(index.minus(1))
                                    ?.requestFocus()
                            } else {
                                focusRequesters
                                    .getOrNull(index.minus(1))
                                    ?.requestFocus()
                            }
                        }
                        false
                    },

                textStyle = textStyle,
                singleLine = true,
                value = enteredNumbers.getOrNull(index)?.trim() ?: "",
                maxLines = 1,
                onValueChange = { value: String ->
                    when {
                        value.isDigitsOnly() -> {
                            if (focusRequesters[index].freeFocus()) {
                                Log.d("onValueChange", "length: ${value.length}")
                                when (value.length) {
                                    1 -> {
                                        enteredNumbers[index] = value.trim()
                                        smsFulled.invoke(enteredNumbers.joinToString(separator = ""))
                                        Log.d("onValueChange", "${enteredNumbers.joinToString("")}")
                                        focusRequesters.getOrNull(index + 1)?.requestFocus()
                                    }

                                    2 -> {
                                        focusRequesters.getOrNull(index + 1)?.requestFocus()
                                    }

                                    else -> {
                                        return@OutlinedTextField
                                    }
                                }
                            }
                        }
                    }
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                ),
            )
            val fulled = enteredNumbers.joinToString(separator = "")
            if (fulled.length == smsCodeLength) {
                LocalSoftwareKeyboardController.current?.hide()
                smsFulled.invoke(fulled)
            }
            Spacer(modifier = Modifier.width(8.dp))
        }
    }
}