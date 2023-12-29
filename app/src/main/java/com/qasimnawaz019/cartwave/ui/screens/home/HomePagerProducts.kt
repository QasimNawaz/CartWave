package com.qasimnawaz019.cartwave.ui.screens.home

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.qasimnawaz019.cartwave.R
import com.qasimnawaz019.cartwave.ui.components.EmptyView
import com.qasimnawaz019.cartwave.ui.components.HomeScreenShimmer
import com.qasimnawaz019.cartwave.utils.gridItems
import com.qasimnawaz019.cartwave.utils.rememberLifecycleEvent
import com.qasimnawaz019.domain.model.ProductsByCategoryItem
import com.qasimnawaz019.domain.utils.NetworkUiState
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun HomePagerProducts(
    onNavigate: (route: String) -> Unit,
    title: String,
    viewModel: HomeScreenViewModel = koinViewModel(
        parameters = { parametersOf(title) }, key = title
    )
) {

    val lazyListState = rememberLazyListState()

    val loading = remember {
        mutableStateOf(true)
    }

    val error = remember {
        mutableStateOf<String?>(null)
    }

    val productsByCategory = remember {
        mutableStateListOf<ProductsByCategoryItem>()
    }

    val lifecycleEvent = rememberLifecycleEvent()

    val productsResponse: NetworkUiState<List<ProductsByCategoryItem>> by viewModel.networkUiState.collectAsStateWithLifecycle()

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
                Log.d("HomeScreen", "NetworkUiState.Success")
                loading.value = false
                (productsResponse as NetworkUiState.Success<List<ProductsByCategoryItem>>).data.let {
                    productsByCategory.clear()
                    productsByCategory.addAll(it)
                }
            }

            else -> {}
        }
    }

    LaunchedEffect(lifecycleEvent) {
        if (lifecycleEvent == Lifecycle.Event.ON_RESUME) {
            if (productsByCategory.isNotEmpty()) {
                viewModel.getProductsGroupBySubCategory(title, true)
            }
        }
    }

    if (!loading.value && !error.value.isNullOrBlank()) {
        EmptyView(
            drawable = R.drawable.ic_network_error,
            text = error.value ?: "Something went wrong"
        )
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(), state = lazyListState
    ) {
        if (loading.value) {
            item {
                HomeScreenShimmer()
            }
        }
        productsByCategory.forEach {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .padding(horizontal = 15.dp, vertical = 6.dp)
                        .background(
                            color = MaterialTheme.colorScheme.primary,
                            shape = MaterialTheme.shapes.small
                        )
                        .padding(horizontal = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = it.category ?: "N/A",
                        fontSize = MaterialTheme.typography.titleMedium.fontSize,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.weight(0.8f)
                    )

                    Text(
                        text = "See All",
                        textAlign = TextAlign.End,
                        fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                        color = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.weight(0.2f)
                    )
                }
            }
            it.products?.let { products ->
                gridItems(
                    data = products,
                    columnCount = 2,
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    modifier = Modifier.padding(horizontal = 10.dp)
                ) { index, product ->
                    ProductRowItem(product = product,
                        onNavigate = onNavigate,
                        onUpdateFavourite = { add ->
                            product.id?.let { _id ->
                                if (add) {
                                    viewModel.addToFavourite(_id)
                                } else {
                                    viewModel.removeFromFavourite(_id)
                                }
                            }
                        })
                }
            }
        }
    }

}