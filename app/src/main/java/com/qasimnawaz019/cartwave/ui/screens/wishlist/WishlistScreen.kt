package com.qasimnawaz019.cartwave.ui.screens.wishlist

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun WishlistScreen() {
    Box(
        contentAlignment = Alignment.TopCenter, modifier = Modifier.fillMaxSize()
    ) {
        Text(text = "Wishlist")
    }
}

@Composable
@Preview
fun ProfileScreenPreview() {
    WishlistScreen()
}