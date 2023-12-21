package com.qasimnawaz019.cartwave.ui.screens.wishlist

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.qasimnawaz019.cartwave.R
import com.qasimnawaz019.cartwave.ui.components.EmptyView
import com.qasimnawaz019.cartwave.ui.components.VerticalGridProductsShimmer
import com.qasimnawaz019.cartwave.ui.screens.home.ProductRowItem
import com.qasimnawaz019.cartwave.utils.rememberLifecycleEvent
import com.qasimnawaz019.domain.model.Product
import org.koin.androidx.compose.getViewModel

@Composable
fun WishlistScreen(
    onNavigate: (route: String) -> Unit, viewModel: WishlistScreenViewModel = getViewModel()
) {

    val lifecycleEvent = rememberLifecycleEvent()
    val favourites: LazyPagingItems<Product> = viewModel.pagingData.collectAsLazyPagingItems()
    val reload: Boolean by viewModel.reLoad.collectAsStateWithLifecycle()

    LaunchedEffect(reload) {
        if (reload) {
            favourites.refresh()
            viewModel.updateReload()
        }
    }
    LaunchedEffect(lifecycleEvent) {
        if (lifecycleEvent == Lifecycle.Event.ON_RESUME) {
            if (favourites.itemCount > 0) {
                favourites.refresh()
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        if (favourites.itemCount == 0 && favourites.loadState.refresh != LoadState.Loading) {
            EmptyView(R.drawable.ic_heart_3d, "Your wishlist is empty")
        }
        FavouritesGrid(
            favourites = favourites, onNavigate = onNavigate
        ) {
            viewModel.removeFromFavourite(it)
        }
    }

}

@Composable
fun FavouritesGrid(
    favourites: LazyPagingItems<Product>,
    onNavigate: (route: String) -> Unit,
    onRemoveFavourite: (id: Int) -> Unit
) {
    LazyVerticalGrid(state = rememberLazyGridState(),
        contentPadding = PaddingValues(vertical = 15.dp, horizontal = 10.dp),
        columns = GridCells.Fixed(2),
        content = {
            items(count = favourites.itemCount) { index ->
                favourites[index]?.let { product ->
                    ProductRowItem(product = product, onNavigate = onNavigate, onUpdateFavourite = {
                        if (!it) {
                            product.id?.let { _id -> onRemoveFavourite.invoke(_id) }
                        }
                    })
                }
            }
            if (favourites.loadState.refresh == LoadState.Loading || favourites.loadState.append == LoadState.Loading) {
                items(2) {
                    VerticalGridProductsShimmer()
                }
            }
        })
}
