package com.qasimnawaz019.cartwave.ui.screens.home

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.qasimnawaz019.cartwave.R
import com.qasimnawaz019.cartwave.ui.components.CartWaveSurface
import com.qasimnawaz019.cartwave.ui.components.ProductShimmer
import com.qasimnawaz019.domain.model.Product
import okhttp3.internal.notify
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(viewModel: HomeScreenViewModel = koinViewModel()) {

    val productPagingItems: LazyPagingItems<Product> =
        viewModel.getProducts().collectAsLazyPagingItems()

    val lazyListState = rememberLazyListState()

    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override suspend fun onPostFling(consumed: Velocity, available: Velocity): Velocity {
                // On scroll ended detection
                Log.d("NestedScroll", "scroll completed")
                return super.onPostFling(consumed, available)
            }
        }
    }

    Scaffold(
        backgroundColor = MaterialTheme.colorScheme.background, modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Column(Modifier.padding(innerPadding)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    model = R.drawable.my_profile,
                    contentDescription = null,
                    modifier = Modifier
                        .size(35.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.FillBounds
                )
                Spacer(modifier = Modifier.width(10.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(color = MaterialTheme.colorScheme.onBackground, text = "Hi, Qasim")
                    Text(
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
                        text = "Let's go shopping"
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Icon(
                    modifier = Modifier.size(25.dp),
                    imageVector = Icons.Outlined.Search,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.width(10.dp))
                Icon(
                    modifier = Modifier.size(25.dp),
                    imageVector = Icons.Outlined.Notifications,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
            LazyColumn(
                modifier = Modifier.nestedScroll(nestedScrollConnection),
            ) {
                item {
                    ImageSlider()
                }
                val cols = 2
                items(productPagingItems.itemSnapshotList.chunked(cols)) { products ->
                    Row {
                        for ((index, product) in products.withIndex()) {
                            Box(modifier = Modifier.fillMaxWidth(1f / (cols - index))) {
                                product?.let {
                                    ProductItem(product = it, onUpdateFavourite = {
                                        Log.d(
                                            "NestedScroll",
                                            "${product.id}: ${product.isFavourite}"
                                        )
                                        if (it.isFavourite) {
                                            it.id?.let { id -> viewModel.removeFavourite(id) }
                                        } else {
                                            viewModel.addToFavourite(it)
                                        }
                                        productPagingItems[index]?.isFavourite =
                                            it.isFavourite.not()
//                                        productPagingItems.refresh()
                                    })
                                }
                            }
                        }
                    }
                }
                when (productPagingItems.loadState.refresh) { //FIRST LOAD
                    is LoadState.Error -> {
                        //state.error to get error message
                    }

                    is LoadState.Loading -> { // Loading UI
                        item {
                            ProductShimmer()
                        }
                    }

                    else -> {}
                }
                when (val state = productPagingItems.loadState.append) { // Pagination
                    is LoadState.Error -> {
                        //TODO Pagination Error Item
                        //state.error to get error message
                    }

                    is LoadState.Loading -> { // Pagination Loading UI
                        item {
                            ProductShimmer()
                        }
                    }

                    else -> {}
                }

//                item {
//                    val itemSize: Dp = (LocalConfiguration.current.screenWidthDp.dp / 2) - 12.dp
//                    FlowRow(maxItemsInEachRow = 2) {
//                        productPagingItems.itemSnapshotList.forEach { product ->
//                            CartWaveSurface(
//                                color = MaterialTheme.colorScheme.surface,
//                                modifier = Modifier
//                                    .padding(5.dp)
//                                    .width(itemSize)
//                                    .height(230.dp),
//                                elevation = 2.dp,
//                                shape = RoundedCornerShape(6.dp)
//                            ) {
//                                Column(modifier = Modifier.fillMaxSize()) {
//                                    Box(
//                                        modifier = Modifier
//                                            .fillMaxWidth()
//                                            .weight(7f),
//                                        contentAlignment = Alignment.TopEnd
//                                    ) {
//                                        AsyncImage(
//                                            model = ImageRequest.Builder(LocalContext.current)
//                                                .data(product?.image).build(),
//                                            modifier = Modifier.fillMaxSize(),
//                                            contentScale = ContentScale.Inside,
//                                            contentDescription = "item image",
//                                        )
//                                    }
//                                    Column(
//                                        modifier = Modifier
//                                            .fillMaxWidth()
//                                            .padding(horizontal = 6.dp)
//                                            .weight(3f)
//                                    ) {
//                                        Text(
//                                            text = product?.title ?: "",
//                                            overflow = TextOverflow.Ellipsis,
//                                            maxLines = 2,
//                                            fontWeight = FontWeight.Bold,
//                                            fontSize = MaterialTheme.typography.titleMedium.fontSize
//                                        )
//                                        Spacer(modifier = Modifier.height(3.dp))
//                                        Row {
//                                            Text(
//                                                modifier = Modifier
//                                                    .weight(7f),
//                                                text = "$ ${product?.price}",
//                                                fontWeight = FontWeight.Bold
//                                            )
//                                            Row(
//                                                modifier = Modifier
//                                                    .weight(3f),
//                                                verticalAlignment = Alignment.CenterVertically,
//                                                horizontalArrangement = Arrangement.End
//                                            ) {
//                                                Icon(
//                                                    painter = painterResource(id = R.drawable.ic_yellow_star),
//                                                    modifier = Modifier
//                                                        .size(15.dp),
//                                                    contentDescription = null,
//                                                    tint = Color.Unspecified
//                                                )
//                                                Spacer(modifier = Modifier.width(2.dp))
//                                                Text(
//                                                    text = "${product?.rating?.rate}",
//                                                )
//                                            }
//                                        }
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
            }
        }

    }
}


@Composable
fun ProductItem(product: Product, onUpdateFavourite: () -> Unit) {
    CartWaveSurface(
        color = MaterialTheme.colorScheme.surface,
        modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth()
            .height(230.dp)
            .clip(shape = MaterialTheme.shapes.medium),
        elevation = 2.dp,
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(7f)
                    .clip(shape = MaterialTheme.shapes.medium), contentAlignment = Alignment.TopEnd
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
                            .clickable { onUpdateFavourite.invoke() },
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
                            .clickable { onUpdateFavourite.invoke() },
                        painter = painterResource(id = R.drawable.ic_heart),
                        contentDescription = null,
                    )
                }
            }
            Spacer(modifier = Modifier.height(4.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 6.dp)
                    .weight(3f)
            ) {
                Text(
                    text = product.title ?: "",
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2,
                    fontWeight = FontWeight.Bold,
                    fontSize = MaterialTheme.typography.titleMedium.fontSize
                )
                Spacer(modifier = Modifier.height(3.dp))
                Row {
                    Text(
                        modifier = Modifier.weight(7f),
                        text = "$ ${product.price}",
                        fontWeight = FontWeight.Bold
                    )
                    Row(
                        modifier = Modifier.weight(3f),
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
                        )
                    }
                }
            }
        }
    }
}

@Composable
@Preview
fun ProfileScreenPreview() {
    HomeScreen()
}