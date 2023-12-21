package com.qasimnawaz019.cartwave.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout

@Composable
fun EmptyView(drawable: Int, text: String) {
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (icon, message) = createRefs()
        Image(
            modifier = Modifier
                .size(200.dp)
                .constrainAs(icon) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                },
            painter = painterResource(id = drawable),
            contentDescription = null,
        )
        Text(
            modifier = Modifier.constrainAs(message) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                top.linkTo(icon.bottom)
            }, text = text,
            fontWeight = FontWeight.Bold,
            fontSize = MaterialTheme.typography.titleLarge.fontSize,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}