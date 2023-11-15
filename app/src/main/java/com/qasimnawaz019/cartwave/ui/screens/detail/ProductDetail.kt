package com.qasimnawaz019.cartwave.ui.screens.detail

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.qasimnawaz019.cartwave.R
import com.qasimnawaz019.cartwave.ui.components.CartWaveSurface
import com.qasimnawaz019.cartwave.ui.components.ProductDetailShimmer
import com.qasimnawaz019.domain.model.Product
import com.qasimnawaz019.domain.utils.NetworkUiState
import org.koin.androidx.compose.getViewModel

@Composable
fun ProductDetail(
    navController: NavHostController,
    productId: Int,
    viewModel: ProductDetailViewModel = getViewModel()
) {

    var loading by remember {
        mutableStateOf(true)
    }
    var product by remember {
        mutableStateOf<Product?>(null)
    }

    var cartQty by remember {
        mutableIntStateOf(0)
    }

    var isFavourite by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(Unit) {
        if (product == null) {
            viewModel.getProductDetail(productId)
        }
    }

    val productResponse: NetworkUiState<Product> by viewModel.networkUiState.collectAsStateWithLifecycle()
    LaunchedEffect(productResponse) {
        when (productResponse) {
            is NetworkUiState.Loading -> {
                loading = true
            }

            is NetworkUiState.Error -> {
                loading = false
            }

            is NetworkUiState.Success -> {
                loading = false
                (productResponse as NetworkUiState.Success<Product>).data.let {
                    Log.d("ProductDetail", "Success: $it")
                    product = it
                    isFavourite = it.isFavourite
                    cartQty = it.cartQty
                }
            }

            else -> {}
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
    ) {

        if (loading) {
            ProductDetailShimmer()
        }
        product?.let {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current).data(it.image).build(),
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .fillMaxWidth()
                    .fillMaxHeight(0.5f),
                contentScale = ContentScale.FillBounds,
                contentDescription = null,
            )
            CartWaveSurface(
                color = MaterialTheme.colorScheme.surfaceVariant,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .fillMaxHeight(0.6f),
                elevation = 20.dp,
                shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp)
            ) {
                Column(
                    modifier = Modifier.padding(
                        start = 20.dp, end = 20.dp, top = 15.dp, bottom = 10.dp
                    )
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.End
                    ) {
                        Column(
                            modifier = Modifier.weight(8f)
                        ) {
                            Text(
                                text = it.title ?: "",
                                fontWeight = FontWeight.Bold,
                                fontSize = MaterialTheme.typography.titleLarge.fontSize,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.End
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_yellow_star),
                                    modifier = Modifier.size(15.dp),
                                    contentDescription = null,
                                    tint = Color.Unspecified
                                )
                                Spacer(modifier = Modifier.width(5.dp))
                                Text(
                                    text = "${it.rating?.rate}",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = MaterialTheme.typography.titleMedium.fontSize,
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                                Spacer(modifier = Modifier.width(2.dp))
                                Text(
                                    text = "(${it.rating?.count} Review)",
                                    fontSize = MaterialTheme.typography.titleMedium.fontSize,
                                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)
                                )
                            }
                        }
                        if (isFavourite) {
                            Icon(
                                modifier = Modifier
                                    .padding(15.dp)
                                    .size(30.dp)
                                    .background(Color.DarkGray, shape = CircleShape)
                                    .padding(6.dp)
                                    .clickable {
                                        isFavourite = isFavourite.not()
                                        it.id?.let { productId ->
                                            viewModel.removeFavourite(
                                                productId
                                            )
                                        }
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
                                        isFavourite = isFavourite.not()
                                        viewModel.addToFavourite(it.copy(isFavourite = isFavourite))
                                    },
                                painter = painterResource(id = R.drawable.ic_heart),
                                contentDescription = null,
                                tint = Color.Red
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        modifier = Modifier
                            .weight(1f)
                            .verticalScroll(rememberScrollState()),
                        text = "${it.description}",
                        fontSize = MaterialTheme.typography.titleMedium.fontSize,
                        color = MaterialTheme.colorScheme.onBackground,
                        fontWeight = FontWeight.Light
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(
                            buildAnnotatedString {
                                withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                                    append("$ ")
                                }
                                withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.onBackground)) {
                                    append("${it.price}")
                                }
                            },
                            fontSize = MaterialTheme.typography.headlineMedium.fontSize,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                        )
                        if (cartQty == 0) {
                            Box(
                                modifier = Modifier
                                    .height(50.dp)
                                    .fillMaxWidth()
                                    .weight(1f)
                                    .clip(CircleShape)
                                    .background(color = MaterialTheme.colorScheme.primary)
                                    .clickable {
                                        cartQty++
                                        viewModel.addToCart(it.copy(cartQty = cartQty))
                                    }, contentAlignment = Alignment.Center
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceEvenly
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_cart),
                                        modifier = Modifier.size(25.dp),
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.onPrimary
                                    )
                                    Text(
                                        text = "Add to Cart",
                                        color = MaterialTheme.colorScheme.onPrimary
                                    )
                                }
                            }
                        } else {
                            Box(
                                modifier = Modifier
                                    .height(50.dp)
                                    .fillMaxWidth()
                                    .weight(1f)
                                    .clip(CircleShape)
                                    .background(color = MaterialTheme.colorScheme.secondaryContainer),
                                contentAlignment = Alignment.Center
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceEvenly
                                ) {
                                    IconButton(modifier = Modifier
                                        .size(35.dp)
                                        .clip(CircleShape)
                                        .background(color = MaterialTheme.colorScheme.secondary)
                                        .padding(2.dp), onClick = {
                                        cartQty--
                                        if (cartQty > 1) {
                                            viewModel.addToCart(it.copy(cartQty = cartQty))
                                        } else {
                                            it.id?.let { productId ->
                                                viewModel.removeFromCart(productId)
                                            }
                                        }
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
                                        fontSize = MaterialTheme.typography.headlineMedium.fontSize,
                                        fontWeight = FontWeight.Bold
                                    )
                                    IconButton(modifier = Modifier
                                        .size(35.dp)
                                        .clip(CircleShape)
                                        .background(color = MaterialTheme.colorScheme.primary)
                                        .padding(2.dp), onClick = {
                                        cartQty++
                                        viewModel.addToCart(it.copy(cartQty = cartQty))
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
            }
        }
    }

}
