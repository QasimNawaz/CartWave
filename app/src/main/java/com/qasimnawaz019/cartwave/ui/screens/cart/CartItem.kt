package com.qasimnawaz019.cartwave.ui.screens.cart

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.qasimnawaz019.cartwave.R
import com.qasimnawaz019.cartwave.ui.components.CartWaveSurface
import com.qasimnawaz019.domain.model.Product
import com.qasimnawaz019.domain.model.Rating

@Composable
fun CartItem(product: Product, onCartQtyUpdate: (product: Product) -> Unit) {
    var cartQty by remember {
        mutableIntStateOf(product.cartQty)
    }
    CartWaveSurface(
        color = MaterialTheme.colorScheme.surfaceVariant,
        modifier = Modifier
            .padding(horizontal = 10.dp, vertical = 5.dp)
            .fillMaxWidth()
            .height(110.dp)
            .clip(shape = MaterialTheme.shapes.medium),
        elevation = 2.dp,
    ) {

        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .padding(6.dp),
        ) {
            val (rightSpacer, imageContainer, title, price, qtyContainer) = createRefs()
            AsyncImage(
                modifier = Modifier
                    .size(100.dp)
                    .clip(shape = MaterialTheme.shapes.medium)
                    .constrainAs(imageContainer) {
                        start.linkTo(parent.start)
                    },
                model = ImageRequest.Builder(LocalContext.current).data(product.image).build(),
                contentScale = ContentScale.Crop,
                contentDescription = null,
            )
//            Box(
//                modifier = Modifier
//                    .size(100.dp)
//                    .clip(shape = MaterialTheme.shapes.medium)
//                    .constrainAs(imageContainer) {
//                        start.linkTo(parent.start)
//                    },
//            ) {
//                AsyncImage(
//                    modifier = Modifier
//                        .size(100.dp)
//                        .clip(shape = MaterialTheme.shapes.medium)
//                        .constrainAs(imageContainer) {
//                            start.linkTo(parent.start)
//                        },
//                    model = ImageRequest.Builder(LocalContext.current).data(product.image).build(),
//                    contentScale = ContentScale.Crop,
//                    contentDescription = null,
//                )
//                Icon(
//                    modifier = Modifier.align(Alignment.TopStart),
//                    imageVector = Icons.Outlined.Delete,
//                    tint = Color.Red,
//                    contentDescription = null
//                )
//            }
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

            Text(
                modifier = Modifier.constrainAs(price) {
                    end.linkTo(parent.end, 4.dp)
                    bottom.linkTo(parent.bottom, 4.dp)
                },
                text = "$ ${product.price}",
                fontSize = MaterialTheme.typography.titleMedium.fontSize,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontWeight = FontWeight.Bold
            )

            Box(
                modifier = Modifier
                    .height(30.dp)
                    .width(100.dp)
                    .clip(CircleShape)
                    .background(color = MaterialTheme.colorScheme.secondaryContainer)
                    .constrainAs(qtyContainer) {
                        start.linkTo(imageContainer.absoluteRight, 10.dp)
                        bottom.linkTo(parent.bottom, 4.dp)
                    }, contentAlignment = Alignment.Center
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    IconButton(modifier = Modifier
                        .size(20.dp)
                        .clip(CircleShape)
                        .background(color = MaterialTheme.colorScheme.secondary)
                        .padding(2.dp),
                        onClick = {
                            cartQty -= 1
                            onCartQtyUpdate.invoke(product.copy(cartQty = cartQty))
                        }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_minus),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSecondary
                        )
                    }
                    Text(
                        text = "$cartQty",
                        color = MaterialTheme.colorScheme.onSecondaryContainer,
                        fontSize = MaterialTheme.typography.titleSmall.fontSize,
                        fontWeight = FontWeight.Bold
                    )
                    IconButton(modifier = Modifier
                        .size(20.dp)
                        .clip(CircleShape)
                        .background(color = MaterialTheme.colorScheme.primary)
                        .padding(2.dp),
                        onClick = {
                            cartQty += 1
                            onCartQtyUpdate.invoke(product.copy(cartQty = cartQty))
                        }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_plus),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
            }
        }
    }
}