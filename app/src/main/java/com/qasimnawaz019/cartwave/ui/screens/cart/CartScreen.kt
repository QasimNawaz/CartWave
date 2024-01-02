package com.qasimnawaz019.cartwave.ui.screens.cart

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.gson.Gson
import com.qasimnawaz019.cartwave.R
import com.qasimnawaz019.cartwave.ui.components.CartItemShimmer
import com.qasimnawaz019.cartwave.ui.components.EmptyView
import com.qasimnawaz019.cartwave.ui.screens.graphs.MainScreenInfo
import com.qasimnawaz019.cartwave.utils.DashedDivider
import com.qasimnawaz019.domain.model.Product
import com.qasimnawaz019.domain.utils.NetworkUiState
import org.koin.androidx.compose.getViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CartScreen(
    onNavigate: (route: String) -> Unit, viewModel: CartScreenViewModel = getViewModel()
) {

    val lazyListState = rememberLazyListState()

    val products = remember {
        mutableStateListOf<Product>()
    }

    val loading = remember {
        mutableStateOf(true)
    }

    val error = remember {
        mutableStateOf<String?>(null)
    }

    val cartsResponse: NetworkUiState<List<Product>> by viewModel.networkUiState.collectAsStateWithLifecycle()
    val subTotal: Int by viewModel.subTotal.collectAsStateWithLifecycle()

    LaunchedEffect(cartsResponse) {
        when (cartsResponse) {
            is NetworkUiState.Loading -> {
                loading.value = true
            }

            is NetworkUiState.Error -> {
                loading.value = false
                (cartsResponse as NetworkUiState.Error).error.let {
                    error.value = it
                }
            }

            is NetworkUiState.Success -> {
                loading.value = false
                (cartsResponse as NetworkUiState.Success<List<Product>>).data.let {
                    Log.d("CartScreen", "NetworkUiState.Success: ${Gson().toJson(it)}")
                    products.clear()
                    products.addAll(it)
                }
            }

            else -> {}
        }
    }

    val scaffoldState = rememberBottomSheetScaffoldState(
//        bottomSheetState = BottomSheetState(BottomSheetValue.Collapsed)
    )

    BottomSheetScaffold(backgroundColor = MaterialTheme.colorScheme.background,
        scaffoldState = scaffoldState,
        drawerGesturesEnabled = scaffoldState.drawerState.isOpen,
        sheetShape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp),
        sheetElevation = 20.dp,
        sheetPeekHeight = 120.dp,
        sheetContent = {
            Column(
                modifier = Modifier
                    .heightIn(min = 120.dp, max = 225.dp)
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(
                    modifier = Modifier
                        .height(3.dp)
                        .width(70.dp)
                        .background(MaterialTheme.colorScheme.onPrimaryContainer)
                )
                AnimatedVisibility(visible = scaffoldState.bottomSheetState.isExpanded) {
                    Column {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)
                        ) {
                            Text(
                                modifier = Modifier.weight(1f),
                                fontSize = MaterialTheme.typography.titleMedium.fontSize,
                                color = MaterialTheme.colorScheme.onPrimaryContainer,
                                text = "Subtotal"
                            )
                            Text(
                                modifier = Modifier.weight(1f),
                                fontWeight = FontWeight.Bold,
                                fontSize = MaterialTheme.typography.titleMedium.fontSize,
                                color = MaterialTheme.colorScheme.onPrimaryContainer,
                                textAlign = TextAlign.End,
                                text = "$ $subTotal"
                            )
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)
                        ) {
                            Text(
                                modifier = Modifier.weight(1f),
                                fontSize = MaterialTheme.typography.titleMedium.fontSize,
                                color = MaterialTheme.colorScheme.onPrimaryContainer,
                                text = "Shipping"
                            )
                            Text(
                                modifier = Modifier.weight(1f),
                                fontWeight = FontWeight.Bold,
                                fontSize = MaterialTheme.typography.titleMedium.fontSize,
                                color = MaterialTheme.colorScheme.onPrimaryContainer,
                                textAlign = TextAlign.End,
                                text = "$ ${if (subTotal != 0) "9" else "0"}"
                            )
                        }
                        DashedDivider(
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            thickness = 1.dp,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        )
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    Text(
                        modifier = Modifier.weight(1f),
                        fontSize = MaterialTheme.typography.titleMedium.fontSize,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        text = "Total amount"
                    )
                    Text(
                        modifier = Modifier.weight(1f),
                        fontWeight = FontWeight.Bold,
                        fontSize = MaterialTheme.typography.titleMedium.fontSize,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        textAlign = TextAlign.End,
                        text = "$ ${if (subTotal != 0) subTotal + 9 else "0"}"
                    )
                }
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("Checkout"), onClick = {
                        onNavigate.invoke(MainScreenInfo.CheckOut.route)
                    }, colors = ButtonDefaults.buttonColors(
                        contentColor = MaterialTheme.colorScheme.onPrimary,
                        backgroundColor = MaterialTheme.colorScheme.primary,
                        disabledContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        disabledBackgroundColor = MaterialTheme.colorScheme.surfaceVariant
                    ), enabled = subTotal != 0
                ) {
                    Text(text = "Checkout")
                }
            }
        }) {
        if (!loading.value && !error.value.isNullOrBlank()) {
            EmptyView(
                drawable = R.drawable.ic_network_error, text = error.value ?: "Something went wrong"
            )
        } else if (products.isEmpty() && !loading.value) {
            EmptyView(R.drawable.ic_empty_cart, "Your cart is empty")
        }
        LazyColumn(
            state = lazyListState, modifier = Modifier
                .fillMaxSize()
                .padding(start = 10.dp, end = 10.dp, bottom = 120.dp)
        ) {
            if (loading.value) {
                repeat(3) {
                    item {
                        CartItemShimmer()
                    }
                }
            }
            itemsIndexed(items = products) { index, product ->
                key(product.id) {
                    CartItem(product, onCartQtyUpdate = { cartQty ->
                        product.id?.let { _id ->
                            if (cartQty != 0) {
                                viewModel.addToCart(_id,
                                    cartQty,
                                    products.toMutableStateList().apply {
                                        this[index] = product.copy(cartQty = cartQty)
                                    })
                            } else {
                                viewModel.removeFromCart(_id, products.toMutableStateList().apply {
                                    removeAt(index)
                                })
                            }
                        }
                    })
                }
            }
        }
    }
}
