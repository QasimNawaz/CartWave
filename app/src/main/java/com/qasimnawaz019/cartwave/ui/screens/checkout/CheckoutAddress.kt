package com.qasimnawaz019.cartwave.ui.screens.checkout

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.qasimnawaz019.cartwave.R

@Composable
fun CheckoutAddress(navController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(110.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize(),
        ) {
            val (rightSpacer, addressTitle, edit, map, title, address) = createRefs()

            Spacer(modifier = Modifier
                .width(0.dp)
                .wrapContentHeight()
                .constrainAs(rightSpacer) {
                    start.linkTo(parent.end)
                })

            Text(
                modifier = Modifier.constrainAs(addressTitle) {
                    start.linkTo(parent.start)
                },
                text = "Address",
                fontSize = MaterialTheme.typography.titleMedium.fontSize,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontWeight = FontWeight.Bold
            )
            Text(
                modifier = Modifier.constrainAs(edit) {
                    end.linkTo(parent.end)
                },
                text = "Edit",
                fontSize = MaterialTheme.typography.titleSmall.fontSize,
                color = MaterialTheme.colorScheme.primary
            )
            AsyncImage(
                modifier = Modifier
                    .size(75.dp)
                    .clip(shape = MaterialTheme.shapes.medium)
                    .constrainAs(map) {
                        start.linkTo(parent.start)
                        top.linkTo(addressTitle.bottom, 12.dp)
                    },
                model = R.drawable.map,
                contentDescription = null,
                contentScale = ContentScale.FillBounds
            )

            Text(
                modifier = Modifier.constrainAs(title) {
                    start.linkTo(map.absoluteRight, 10.dp)
                    end.linkTo(rightSpacer.absoluteLeft)
                    top.linkTo(addressTitle.bottom, 12.dp)
                    width = Dimension.fillToConstraints
                },
                text = "Home",
                fontSize = MaterialTheme.typography.titleLarge.fontSize,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontWeight = FontWeight.Bold
            )

            Text(
                modifier = Modifier
                    .constrainAs(address) {
                        start.linkTo(map.absoluteRight, 10.dp)
                        end.linkTo(rightSpacer.absoluteLeft)
                        top.linkTo(title.bottom, 5.dp)
                        width = Dimension.fillToConstraints
                    },
                text = "Sharifabad, Block 1 Gulberg Town, Karachi, Karachi City, Sindh, Pakistan",
                fontSize = MaterialTheme.typography.titleSmall.fontSize,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )

        }
    }
}