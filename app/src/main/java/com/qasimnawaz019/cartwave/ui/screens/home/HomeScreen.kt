package com.qasimnawaz019.cartwave.ui.screens.home

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridItemSpanScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.qasimnawaz019.cartwave.R
import com.qasimnawaz019.cartwave.ui.components.CartWaveSurface
import com.qasimnawaz019.cartwave.ui.components.VerticalGridProductsShimmer
import com.qasimnawaz019.cartwave.ui.screens.graphs.MainScreenInfo
import com.qasimnawaz019.cartwave.utils.gridItems
import com.qasimnawaz019.domain.model.Product
import com.qasimnawaz019.domain.utils.NetworkUiState
import org.koin.androidx.compose.getViewModel

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun HomeScreen(
    onNavigate: (route: String) -> Unit, viewModel: HomeScreenViewModel = getViewModel()
) {
    val lazyListState = rememberLazyListState()
//    val products = remember {
//        mutableStateMapOf<Int, Product>()
//    }
    val products = remember {
        mutableStateListOf<Product>()
    }
    val loading = remember {
        mutableStateOf(true)
    }
    val error = remember {
        mutableStateOf("")
    }
    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override suspend fun onPostFling(
                consumed: Velocity, available: Velocity
            ): Velocity {
                if (!lazyListState.canScrollForward && (products.size + 10) <= 20) {
                    viewModel.getProducts(products.size + 10)
                }
                return super.onPostFling(consumed, available)
            }
        }
    }

    val productsResponse: NetworkUiState<List<Product>> by viewModel.networkUiState.collectAsStateWithLifecycle()
    LaunchedEffect(productsResponse) {
        when (productsResponse) {
            is NetworkUiState.Loading -> {
                loading.value = true
            }

            is NetworkUiState.Error -> {
                loading.value = false
                (productsResponse as NetworkUiState.Error).error.let {
                    error.value = it
                }
            }

            is NetworkUiState.Success -> {
                loading.value = false
                (productsResponse as NetworkUiState.Success<List<Product>>).data.let {
                    products.addAll(it)
                    Log.d("HomeScren", "Success")
                }
            }

            else -> {}
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
                modifier = Modifier.nestedScroll(nestedScrollConnection), state = lazyListState
            ) {
                item {
                    ImageSlider()
                }
                gridItems(
                    data = products,
                    columnCount = 2,
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    modifier = Modifier.padding(horizontal = 8.dp)
                ) { index, product ->
                    ProductItem(product = product, onUpdateFavourite = {
                        products.set(index, product.copy(isFavourite = product.isFavourite.not()))
                        viewModel.addToFavourite(products.get(index))
                    }) {
                        onNavigate.invoke("${MainScreenInfo.ProductDetail.route}/${product.id}")
                    }
                }
//                itemsIndexed(products) { index, product ->
//                    val itemSize: Dp = (LocalConfiguration.current.screenWidthDp.dp / 2) - 12.dp
//                    ProductItem(product = product, width = itemSize, onUpdateFavourite = {
//                        products.set(index, product.copy(isFavourite = product.isFavourite.not()))
//                        viewModel.addToFavourite(products.get(index))
//                    }) {
//                        onNavigate.invoke("${MainScreenInfo.ProductDetail.route}/${product.id}")
//                    }
//                }

//                item {
//                    val itemSize: Dp = (LocalConfiguration.current.screenWidthDp.dp / 2) - 12.dp
//                    FlowRow(maxItemsInEachRow = 2) {
//                        products.forEachIndexed { index, product ->
//                            ProductItem(product = product, width = itemSize, onUpdateFavourite = {
//                                products.set(index, product.copy(isFavourite = product.isFavourite.not()))
//                                viewModel.addToFavourite(products.get(index))
//                            }) {
//                                onNavigate.invoke("${MainScreenInfo.ProductDetail.route}/${product.id}")
//                            }
//                        }
////                        products.forEach {
////                            ProductItem(product = it.value, width = itemSize, {
////                                products.set(
////                                    it.key,
////                                    it.value.copy(isFavourite = it.value.isFavourite.not())
////                                )
////                                viewModel.updateFavouriteStatus(
////                                    it.value,
////                                    products.values.toMutableStateList()
////                                )
////                            }, {
////                                onNavigate.invoke("${MainScreenInfo.ProductDetail.route}/${it.key}")
////                            })
////                        }
//                    }
//                }
                item {
                    if (loading.value) {
                        VerticalGridProductsShimmer()
                    }
                }
            }
        }

    }
}


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
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
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
                    fontSize = MaterialTheme.typography.titleMedium.fontSize,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.height(3.dp))
                Row {
                    Text(
                        modifier = Modifier.weight(7f),
                        text = "$ ${product.price}",
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
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
                            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)
                        )
                    }
                }
            }
        }
    }
}

//@Composable
//fun ProductItem(
//    product: Product,
//    width: Dp,
//    onUpdateFavourite: () -> Unit,
//    onDetailNav: () -> Unit
//) {
//    CartWaveSurface(
//        color = MaterialTheme.colorScheme.surfaceVariant,
//        modifier = Modifier
//            .padding(5.dp)
//            .width(width)
//            .height(230.dp)
//            .clip(shape = MaterialTheme.shapes.medium)
//            .clickable {
//                Log.d("HomeScren", "onDetailNav.invoke()")
//                onDetailNav.invoke()
//            },
//        elevation = 2.dp,
//    ) {
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//        ) {
//            Box(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .weight(7f)
//                    .clip(shape = MaterialTheme.shapes.medium),
//                contentAlignment = Alignment.TopEnd
//            ) {
//                AsyncImage(
//                    model = ImageRequest.Builder(LocalContext.current).data(product.image)
//                        .build(),
//                    modifier = Modifier.fillMaxSize(),
//                    contentScale = ContentScale.Crop,
//                    contentDescription = null,
//                )
//                if (product.isFavourite) {
//                    Icon(
//                        modifier = Modifier
//                            .padding(15.dp)
//                            .size(30.dp)
//                            .background(Color.DarkGray, shape = CircleShape)
//                            .padding(6.dp)
//                            .clickable {
//                                onUpdateFavourite.invoke()
//                            },
//                        painter = painterResource(id = R.drawable.ic_heart_filled),
//                        contentDescription = null,
//                        tint = Color.Red,
//                    )
//                } else {
//                    Icon(
//                        modifier = Modifier
//                            .padding(15.dp)
//                            .size(30.dp)
//                            .background(Color.DarkGray, shape = CircleShape)
//                            .padding(6.dp)
//                            .clickable {
//                                onUpdateFavourite.invoke()
//                            },
//                        painter = painterResource(id = R.drawable.ic_heart),
//                        contentDescription = null,
//                        tint = Color.Red
//                    )
//                }
//            }
//            Spacer(modifier = Modifier.height(4.dp))
//            Column(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(horizontal = 6.dp)
//                    .weight(3f)
//            ) {
//                Text(
//                    text = product.title ?: "",
//                    overflow = TextOverflow.Ellipsis,
//                    maxLines = 2,
//                    fontWeight = FontWeight.Bold,
//                    fontSize = MaterialTheme.typography.titleMedium.fontSize,
//                    color = MaterialTheme.colorScheme.onBackground
//                )
//                Spacer(modifier = Modifier.height(3.dp))
//                Row {
//                    Text(
//                        modifier = Modifier.weight(7f),
//                        text = "$ ${product.price}",
//                        fontWeight = FontWeight.Bold,
//                        color = MaterialTheme.colorScheme.onBackground
//                    )
//                    Row(
//                        modifier = Modifier.weight(3f),
//                        verticalAlignment = Alignment.CenterVertically,
//                        horizontalArrangement = Arrangement.End
//                    ) {
//                        Icon(
//                            painter = painterResource(id = R.drawable.ic_yellow_star),
//                            modifier = Modifier.size(15.dp),
//                            contentDescription = null,
//                            tint = Color.Unspecified
//                        )
//                        Spacer(modifier = Modifier.width(2.dp))
//                        Text(
//                            text = "${product.rating?.rate}",
//                            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)
//                        )
//                    }
//                }
//            }
//        }
//    }
//}