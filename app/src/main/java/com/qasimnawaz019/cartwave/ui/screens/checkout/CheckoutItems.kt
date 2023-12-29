package com.qasimnawaz019.cartwave.ui.screens.checkout

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.qasimnawaz019.cartwave.ui.components.CartWaveSurface
import com.qasimnawaz019.domain.model.Product

@Composable
fun CheckoutItems(modifier: Modifier = Modifier, productsResponse: List<Product>) {
    LazyColumn(modifier = modifier) {
        item {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Products(${productsResponse.size})",
                fontSize = MaterialTheme.typography.titleMedium.fontSize,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontWeight = FontWeight.Bold
            )
        }
        productsResponse.forEach { product ->
            item { CheckoutItem(product) }
        }
    }
}

@Composable
fun CheckoutItem(product: Product) {
    CartWaveSurface(
        color = MaterialTheme.colorScheme.surfaceVariant,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .height(110.dp)
            .clip(shape = MaterialTheme.shapes.medium),
        elevation = 2.dp,
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .padding(6.dp),
        ) {
            val (rightSpacer, imageContainer, title, price) = createRefs()
            AsyncImage(
                modifier = Modifier
                    .size(100.dp)
                    .clip(shape = MaterialTheme.shapes.medium)
                    .constrainAs(imageContainer) {
                        start.linkTo(parent.start)
                    },
                model = ImageRequest.Builder(LocalContext.current)
                    .diskCachePolicy(CachePolicy.ENABLED)
                    .memoryCachePolicy(CachePolicy.ENABLED)
                    .data(product.images.firstOrNull()).build(),
                contentScale = ContentScale.Crop,
                contentDescription = null,
            )
            Spacer(modifier = Modifier
                .width(0.dp)
                .fillMaxHeight()
                .constrainAs(rightSpacer) {
                    start.linkTo(parent.end)
                })
            Text(
                modifier = Modifier.constrainAs(title) {
                    start.linkTo(imageContainer.absoluteRight, 10.dp)
                    end.linkTo(rightSpacer.absoluteLeft)
                    width = Dimension.fillToConstraints
                },
                text = "${product.title}",
                fontSize = MaterialTheme.typography.titleMedium.fontSize,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontWeight = FontWeight.Bold
            )

            Row(modifier = Modifier.constrainAs(price) {
                start.linkTo(imageContainer.absoluteRight, 10.dp)
                end.linkTo(rightSpacer.absoluteLeft)
                width = Dimension.fillToConstraints
                bottom.linkTo(parent.bottom, 4.dp)
            }) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = "Quantity ${product.cartQty}",
                    fontSize = MaterialTheme.typography.titleMedium.fontSize,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    modifier = Modifier.weight(1f),
                    text = "$ ${product.sellingPrice}",
                    fontSize = MaterialTheme.typography.titleMedium.fontSize,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.End
                )
            }
        }
    }
}