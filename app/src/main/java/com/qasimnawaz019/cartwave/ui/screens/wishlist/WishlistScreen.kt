package com.qasimnawaz019.cartwave.ui.screens.wishlist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Scaffold
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.qasimnawaz019.cartwave.ui.screens.graphs.MainScreenInfo
import com.qasimnawaz019.cartwave.ui.screens.home.ProductItem
import com.qasimnawaz019.cartwave.utils.gridItems
import com.qasimnawaz019.domain.model.Product
import org.koin.androidx.compose.getViewModel

@Composable
fun WishlistScreen(
    onNavigate: (route: String) -> Unit,
    viewModel: WishlistScreenViewModel = getViewModel()
) {

    val lazyListState = rememberLazyListState()

    val productsResponse: List<Product> by viewModel.favouriteProducts.collectAsStateWithLifecycle()

    Scaffold(
        backgroundColor = MaterialTheme.colorScheme.background, modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        LazyColumn(
            state = lazyListState,
            modifier = Modifier.padding(innerPadding)
        ) {
            gridItems(
                data = productsResponse,
                columnCount = 2,
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier.padding(horizontal = 8.dp)
            ) { index, product ->
                ProductItem(product = product, onUpdateFavourite = {
                    val updatedList = productsResponse.toMutableList()
                    updatedList.removeAt(index)
                    if (product.isFavourite) {
                        product.id?.let { viewModel.removeFavourite(it, updatedList) }
                    }
                }) {
                    onNavigate.invoke("${MainScreenInfo.ProductDetail.route}/${product.id}")
                }
            }
        }
    }

}