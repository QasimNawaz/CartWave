package com.qasimnawaz019.cartwave.ui.screens.detail

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.qasimnawaz019.cartwave.R
import com.qasimnawaz019.cartwave.ui.components.CartWaveSurface
import com.qasimnawaz019.cartwave.ui.components.ProductDetailShimmer
import com.qasimnawaz019.domain.model.Product
import com.qasimnawaz019.domain.utils.NetworkUiState
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun ProductDetail(
    navController: NavHostController,
    productId: Int,
    viewModel: ProductDetailViewModel = getViewModel(parameters = { parametersOf(productId) })
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
    ) {

        if (loading) {
            ProductDetailShimmer()
        }
        product?.let {
            ProductImages(
                Modifier
                    .align(Alignment.TopCenter)
                    .fillMaxWidth()
                    .fillMaxHeight(0.5f), it.images,
                onDismiss = {
                    navController.popBackStack()
                }
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
                                    text = "${it.averageRating}",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = MaterialTheme.typography.titleMedium.fontSize,
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                                Spacer(modifier = Modifier.width(10.dp))
                                Text(
                                    text = "Brand: ${it.brand}",
                                    fontSize = MaterialTheme.typography.titleMedium.fontSize,
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                            }
                        }
                        Icon(
                            modifier = Modifier
                                .padding(15.dp)
                                .size(36.dp)
                                .background(Color.DarkGray, shape = CircleShape)
                                .padding(6.dp)
                                .clickable {
                                    it.id?.let { _id ->
                                        if (isFavourite) viewModel.removeFromFavourite(_id) else viewModel.addToFavourite(
                                            _id
                                        )
                                    }
                                },
                            painter = painterResource(id = if (isFavourite) R.drawable.ic_heart_filled else R.drawable.ic_heart),
                            contentDescription = null,
                            tint = Color.Red,
                        )
                    }
                    it.productDetails?.let {
                        LazyRow(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 10.dp),
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            product?.productDetails?.let { map ->
                                items(map) { detail ->
                                    if (detail.keys.first() != "Style Code") {
                                        Row(
                                            modifier = Modifier.clip(shape = MaterialTheme.shapes.small)
                                        ) {
                                            Text(
                                                modifier = Modifier
                                                    .background(MaterialTheme.colorScheme.primary)
                                                    .padding(6.dp),
                                                text = "${detail.keys.first()} ",
                                                fontSize = MaterialTheme.typography.titleSmall.fontSize,
                                                color = MaterialTheme.colorScheme.onPrimary
                                            )
                                            Text(
                                                modifier = Modifier
                                                    .background(
                                                        MaterialTheme.colorScheme.primary.copy(
                                                            alpha = 0.5f
                                                        )
                                                    )
                                                    .padding(6.dp),
                                                text = " ${detail.values.first()}",
                                                fontSize = MaterialTheme.typography.titleSmall.fontSize,
                                                color = MaterialTheme.colorScheme.primary
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
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
                                withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                                    append("${it.sellingPrice} ")
                                }
                                if (!it.actualPrice.isNullOrEmpty()) {
                                    withStyle(
                                        style = SpanStyle(
                                            fontSize = MaterialTheme.typography.titleMedium.fontSize,
                                            color = MaterialTheme.colorScheme.onBackground,
                                            textDecoration = TextDecoration.LineThrough
                                        )
                                    ) {
                                        append(it.actualPrice ?: "")
                                    }
                                }
                            },
                            fontSize = MaterialTheme.typography.headlineSmall.fontSize,
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
                                        it.id?.let { _id -> viewModel.addToCart(_id, cartQty + 1) }
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
                                        it.id?.let { _id ->
                                            if (cartQty > 1) {
                                                viewModel.addToCart(_id, cartQty - 1)
                                            } else {
                                                viewModel.removeFromCart(_id)
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
                                        it.id?.let { _id ->
                                            viewModel.addToCart(
                                                _id, cartQty + 1
                                            )
                                        }
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

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProductImages(modifier: Modifier, images: List<String>, onDismiss: () -> Unit) {
    val pagerState = rememberPagerState { images.size }
    val coroutineScope = rememberCoroutineScope()
    Box(modifier = modifier) {

        HorizontalPager(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center),
            state = pagerState,
        ) { position ->
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .diskCachePolicy(CachePolicy.ENABLED)
                    .memoryCachePolicy(CachePolicy.ENABLED)
                    .data(images[position]).build(),
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .fillMaxSize(),
                contentScale = ContentScale.FillBounds,
                contentDescription = null,
            )
        }

        IconButton(modifier = Modifier
            .padding(25.dp)
            .size(40.dp)
            .align(Alignment.TopStart)
            .clip(CircleShape)
            .background(color = Color.Gray.copy(alpha = 0.5f)), onClick = {
            onDismiss.invoke()
        }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_arrow_left),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }

        LazyRow(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(start = 30.dp, end = 30.dp, bottom = 100.dp)
                .background(Color.Gray.copy(alpha = 0.5f), shape = MaterialTheme.shapes.small)
                .padding(vertical = 6.dp, horizontal = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            images.forEachIndexed { index, s ->
                item {
                    OutlinedCard(
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White,
                        ),
                        border = BorderStroke(
                            width = 1.dp,
                            color = if (pagerState.settledPage == index) Color.Black else Color.White
                        ),
                        modifier = Modifier
                            .size(30.dp)
                            .clickable {
                                coroutineScope.launch {
                                    pagerState.animateScrollToPage(index)
                                }
                            },
                        shape = MaterialTheme.shapes.small,
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    ) {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .diskCachePolicy(CachePolicy.ENABLED)
                                .memoryCachePolicy(CachePolicy.ENABLED)
                                .data(s).build(),
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Fit,
                            contentDescription = null,
                        )
                    }
                }
            }
        }
    }
}
