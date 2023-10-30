package com.qasimnawaz019.cartwave.ui.screens.cart

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun CartScreen() {
    Box(
        contentAlignment = Alignment.TopCenter, modifier = Modifier.fillMaxSize()
    ) {
        Text(text = "My Cart")
    }
}

@Composable
@Preview
fun ProfileScreenPreview() {
    CartScreen()
}