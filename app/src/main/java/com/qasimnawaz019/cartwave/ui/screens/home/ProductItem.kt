package com.qasimnawaz019.cartwave.ui.screens.home

import android.util.Log
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
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.qasimnawaz019.cartwave.R
import com.qasimnawaz019.cartwave.ui.components.CartWaveSurface
import com.qasimnawaz019.domain.model.Product

@Composable
fun ProductItem(
    product: Product, onUpdateFavourite: () -> Unit, onDetailNav: () -> Unit
) {

    CartWaveSurface(
        color = MaterialTheme.colorScheme.surfaceVariant,
        modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth()
            .height(230.dp)
            .clip(shape = MaterialTheme.shapes.medium)
            .clickable {
                Log.d("HomeScren", "onDetailNav.invoke()")
                onDetailNav.invoke()
            },
        elevation = 2.dp,
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize(),
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
                contentAlignment = Alignment.TopEnd
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current).data(product.image).build(),
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop,
                    contentDescription = null,
                )
                if (product.isFavourite) {
                    Icon(
                        modifier = Modifier
                            .padding(15.dp)
                            .size(30.dp)
                            .background(Color.DarkGray, shape = CircleShape)
                            .padding(6.dp)
                            .clickable {
                                onUpdateFavourite.invoke()
                            },
                        painter = painterResource(id = R.drawable.ic_heart_filled),
                        contentDescription = null,
                        tint = Color.Red,
                    )
                } else {
                    Icon(
                        modifier = Modifier
                            .padding(15.dp)
                            .size(30.dp)
                            .background(Color.DarkGray, shape = CircleShape)
                            .padding(6.dp)
                            .clickable {
                                onUpdateFavourite.invoke()
                            },
                        painter = painterResource(id = R.drawable.ic_heart),
                        contentDescription = null,
                        tint = Color.Red
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
                modifier = Modifier.constrainAs(price) {
                    start.linkTo(parent.start, 6.dp)
                    bottom.linkTo(parent.bottom, 4.dp)
                },
                text = "$ ${product.price}",
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
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
                Spacer(modifier = Modifier.width(2.dp))
                Text(
                    text = "${product.rating?.rate}",
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)
                )
            }

        }
    }
}