package com.qasimnawaz019.cartwave.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.qasimnawaz019.cartwave.R
import com.qasimnawaz019.cartwave.ui.screens.graphs.MainScreenInfo
import com.qasimnawaz019.domain.model.Product

@Composable
fun ProductRowItem(
    product: Product,
    onNavigate: (route: String) -> Unit,
    onUpdateFavourite: (add: Boolean) -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
            .padding(horizontal = 6.dp, vertical = 10.dp)
            .clickable {
                onNavigate.invoke("${MainScreenInfo.ProductDetail.route}/${product.id}")
            },
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
    ) {
        ConstraintLayout(
            modifier = Modifier.fillMaxSize()
        ) {
            val (imageContainer, title, price, ratingContainer) = createRefs()

            Box(
                modifier = Modifier
                    .constrainAs(imageContainer) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    .fillMaxWidth()
                    .fillMaxHeight(0.7f)
                    .height(0.dp)
                    .clip(shape = MaterialTheme.shapes.medium),
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(product.images.firstOrNull()).diskCachePolicy(CachePolicy.ENABLED)
                        .memoryCachePolicy(CachePolicy.ENABLED).build(),
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop,
                    contentDescription = null,
                )
                if (product.isFavourite) {
                    Icon(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(10.dp)
                            .size(36.dp)
                            .background(Color.DarkGray, shape = CircleShape)
                            .padding(6.dp)
                            .clickable {
                                onUpdateFavourite.invoke(false)
                            },
                        painter = painterResource(id = R.drawable.ic_heart_filled),
                        contentDescription = null,
                        tint = Color.Red,
                    )
                } else {
                    Icon(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(10.dp)
                            .size(36.dp)
                            .background(Color.DarkGray, shape = CircleShape)
                            .padding(6.dp)
                            .clickable {
                                onUpdateFavourite.invoke(true)
                            },
                        painter = painterResource(id = R.drawable.ic_heart),
                        contentDescription = null,
                        tint = Color.Red
                    )
                }

                if (!product.discount.isNullOrEmpty()) {
                    Text(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(8.dp)
                            .background(Color.DarkGray, shape = CircleShape)
                            .padding(horizontal = 12.dp, vertical = 3.dp),
                        text = product.discount ?: "",
                        color = Color.White,
                        fontSize = MaterialTheme.typography.titleSmall.fontSize,
                    )
                }
            }

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 6.dp)
                    .constrainAs(title) {
                        top.linkTo(imageContainer.bottom, 2.dp)
                    },
                text = product.title ?: "",
                overflow = TextOverflow.Ellipsis,
                maxLines = 2,
                fontWeight = FontWeight.Bold,
                fontSize = MaterialTheme.typography.titleMedium.fontSize,
                color = MaterialTheme.colorScheme.onBackground
            )

            Text(
                buildAnnotatedString {
                    append("$ ")
                    if (!product.actualPrice.isNullOrEmpty()) {
                        withStyle(style = SpanStyle(textDecoration = TextDecoration.LineThrough)) {
                            append(product.actualPrice ?: "")
                        }
                    }
                    append(" ${product.sellingPrice}")
                },
                modifier = Modifier.constrainAs(price) {
                    start.linkTo(parent.start, 6.dp)
                    bottom.linkTo(parent.bottom, 4.dp)
                },
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.Bold,
                fontSize = MaterialTheme.typography.titleSmall.fontSize,
            )
            Row(
                modifier = Modifier.constrainAs(ratingContainer) {
                    end.linkTo(parent.end, 6.dp)
                    bottom.linkTo(parent.bottom, 4.dp)
                },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_yellow_star),
                    modifier = Modifier.size(15.dp),
                    contentDescription = null,
                    tint = Color.Unspecified
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "${product.averageRating}",
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
                    fontSize = MaterialTheme.typography.titleSmall.fontSize,
                )
            }
        }
    }
}