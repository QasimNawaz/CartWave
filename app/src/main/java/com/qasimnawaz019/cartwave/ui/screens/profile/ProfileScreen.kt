package com.qasimnawaz019.cartwave.ui.screens.profile

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ProfileScreen() {
    Box(
        contentAlignment = Alignment.TopCenter, modifier = Modifier.fillMaxSize()
    ) {
        Text(text = "Profile")
    }
}

@Composable
@Preview
fun ProfileScreenPreview() {
    ProfileScreen()
}