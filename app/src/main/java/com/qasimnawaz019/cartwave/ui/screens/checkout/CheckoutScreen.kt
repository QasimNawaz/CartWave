package com.qasimnawaz019.cartwave.ui.screens.checkout

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.qasimnawaz019.cartwave.R
import com.qasimnawaz019.cartwave.ui.components.AddressShimmer
import com.qasimnawaz019.cartwave.ui.components.AlertMessageDialog
import com.qasimnawaz019.cartwave.ui.components.CartItemShimmer
import com.qasimnawaz019.cartwave.ui.components.CartWaveScaffold
import com.qasimnawaz019.cartwave.ui.components.LoadingDialog
import com.qasimnawaz019.cartwave.utils.rememberLifecycleEvent
import com.qasimnawaz019.domain.dto.order.OrderProduct
import com.qasimnawaz019.domain.dto.order.PlaceOrderRequestDto
import com.qasimnawaz019.domain.model.Address
import com.qasimnawaz019.domain.model.Product
import com.qasimnawaz019.domain.utils.NetworkUiState
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckOutScreen(
    navController: NavHostController,
    onNavigate: (route: String) -> Unit,
    viewModel: CheckOutScreenViewModel = getViewModel()
) {
    val lifecycleEvent = rememberLifecycleEvent()

    val products = remember {
        mutableStateListOf<Product>()
    }

    val primaryAddress = remember {
        mutableStateOf<Address?>(null)
    }

    val listLoading = remember {
        mutableStateOf(false)
    }
    val listError = remember {
        mutableStateOf<String?>(null)
    }
    val addressLoading = remember {
        mutableStateOf(false)
    }
    val addressError = remember {
        mutableStateOf<String?>(null)
    }
    val orderLoading = remember {
        mutableStateOf(false)
    }
    val orderError = remember {
        mutableStateOf<String?>(null)
    }
    val sheetState =
        rememberModalBottomSheetState(skipPartiallyExpanded = true, confirmValueChange = { false })
    var orderPlacedBottomSheet by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    val cartsResponse: NetworkUiState<List<Product>> by viewModel.userCartUiState.collectAsStateWithLifecycle()
    val primaryAddressResponse: NetworkUiState<Address> by viewModel.networkUiState.collectAsStateWithLifecycle()
    val totalAmount: Int by viewModel.totalAmount.collectAsStateWithLifecycle()
    val orderResponse: NetworkUiState<String> by viewModel.placeUiState.collectAsStateWithLifecycle()

    LaunchedEffect(lifecycleEvent) {
        if (lifecycleEvent == Lifecycle.Event.ON_RESUME) {
            if (!addressLoading.value) {
                viewModel.getPrimaryAddress()
            }
        }
    }

    LaunchedEffect(cartsResponse) {
        when (cartsResponse) {
            is NetworkUiState.Loading -> {
                listLoading.value = true
            }

            is NetworkUiState.Error -> {
                listLoading.value = false
                (cartsResponse as NetworkUiState.Error).error.let {
                    listError.value = it
                }
            }

            is NetworkUiState.Success -> {
                listLoading.value = false
                (cartsResponse as NetworkUiState.Success<List<Product>>).data.let {
                    products.clear()
                    products.addAll(it)
                }
            }

            else -> {}
        }
    }

    LaunchedEffect(primaryAddressResponse) {
        when (primaryAddressResponse) {
            is NetworkUiState.Loading -> {
                addressLoading.value = true
            }

            is NetworkUiState.Error -> {
                addressLoading.value = false
                (primaryAddressResponse as NetworkUiState.Error).error.let {
                    addressError.value = it
                }
            }

            is NetworkUiState.Success -> {
                addressLoading.value = false
                (primaryAddressResponse as NetworkUiState.Success<Address>).data.let {
                    primaryAddress.value = it
                }
            }

            else -> {}
        }
    }

    LaunchedEffect(orderResponse) {
        when (orderResponse) {
            is NetworkUiState.Loading -> {
                orderLoading.value = true
            }

            is NetworkUiState.Error -> {
                orderLoading.value = false
                (orderResponse as NetworkUiState.Error).error.let {
                    orderError.value = it
                }
            }

            is NetworkUiState.Success -> {
                orderLoading.value = false
                orderPlacedBottomSheet = true
            }

            else -> {}
        }
    }

    if (orderPlacedBottomSheet) {
        ModalBottomSheet(onDismissRequest = {
            orderPlacedBottomSheet = false
        }, sheetState = sheetState) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(500.dp)
                    .padding(horizontal = 12.dp)
            ) {
                Column(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Order Placed",
                        fontSize = MaterialTheme.typography.headlineSmall.fontSize,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground,
                        textAlign = TextAlign.Center
                    )
                    Image(
                        modifier = Modifier.size(200.dp),
                        painter = painterResource(id = R.drawable.ic_success),
                        contentDescription = null,
                    )
                    Text(
                        text = "Your order has been confirmed, we will send you confirmation email shortly.",
                        fontSize = MaterialTheme.typography.bodySmall.fontSize,
                        fontWeight = FontWeight.Light,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
                        textAlign = TextAlign.Center
                    )
                }
                Button(modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .padding(bottom = 12.dp),
                    onClick = {
                        scope.launch { sheetState.hide() }.invokeOnCompletion {
                            if (!sheetState.isVisible) {
                                orderPlacedBottomSheet = false
                                navController.popBackStack()
                            }
                        }
                    }) {
                    Text("Continue Shopping")
                }
            }
        }
    }

    if (orderLoading.value) {
        LoadingDialog()
    }
    if (orderError.value != null) {
        AlertMessageDialog(title = "Something went wrong !",
            message = orderError.value,
            drawable = R.drawable.ic_error_dialog,
            positiveButtonText = "Retry",
            negativeButtonText = "Cancel",
            onPositiveClick = {
                viewModel.placeOrder(
                    PlaceOrderRequestDto(userId = 0,
                        orderDate = "${System.currentTimeMillis()}",
                        shippingAddress = primaryAddress?.value?.address ?: "",
                        orderStatus = "Placed",
                        promoCode = "",
                        totalAmount = totalAmount,
                        paymentMethod = "",
                        products = products.map {
                            OrderProduct(
                                productId = it.id,
                                title = it.title ?: "",
                                price = it.sellingPrice?.toInt() ?: 0,
                                quantity = it.cartQty
                            )
                        })
                )
            },
            onNegativeClick = {

            })
    }
    CartWaveScaffold(modifier = Modifier.fillMaxSize(), topBar = {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .padding(horizontal = 20.dp),
        ) {
            Image(
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurfaceVariant),
                modifier = Modifier
                    .size(20.dp)
                    .align(Alignment.CenterStart)
                    .clickable { navController.popBackStack() },
                painter = painterResource(id = R.drawable.ic_arrow_left),
                contentDescription = null,
            )

            Text(
                modifier = Modifier.align(Alignment.Center),
                text = "Payment",
                fontSize = MaterialTheme.typography.titleMedium.fontSize,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontWeight = FontWeight.Bold
            )
        }
    }) {
        Column(modifier = Modifier.padding(start = 10.dp, end = 10.dp, bottom = 15.dp)) {

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f),
            ) {
                item {
                    if (addressLoading.value) {
                        AddressShimmer()
                    } else {
                        CheckoutAddress(primaryAddress, onNavigate)
                    }
                }
                item {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        text = "Products(${products.size})",
                        fontSize = MaterialTheme.typography.titleMedium.fontSize,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontWeight = FontWeight.Bold
                    )
                }
                if (listLoading.value) {
                    repeat(3) {
                        item {
                            CartItemShimmer()
                        }
                    }
                }
                items(items = products, key = {
                    it.id
                }) { product ->
                    CheckoutItem(product)
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
                    color = MaterialTheme.colorScheme.onBackground,
                    text = "Total amount"
                )
                Text(
                    modifier = Modifier.weight(1f),
                    fontWeight = FontWeight.Bold,
                    fontSize = MaterialTheme.typography.titleMedium.fontSize,
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.End,
                    text = "$ $totalAmount"
                )
            }

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("Place Order"),
                onClick = {
                    viewModel.placeOrder(
                        PlaceOrderRequestDto(userId = 0,
                            orderDate = "${System.currentTimeMillis()}",
                            shippingAddress = primaryAddress?.value?.address ?: "",
                            orderStatus = "Placed",
                            promoCode = "",
                            totalAmount = totalAmount,
                            paymentMethod = "",
                            products = products.map {
                                OrderProduct(
                                    productId = it.id,
                                    title = it.title ?: "",
                                    price = it.sellingPrice?.toInt() ?: 0,
                                    quantity = it.cartQty
                                )
                            })
                    )
                },
                colors = ButtonDefaults.buttonColors(
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                    backgroundColor = MaterialTheme.colorScheme.primary,
                    disabledContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    disabledBackgroundColor = MaterialTheme.colorScheme.surfaceVariant
                ),
            ) {
                Text(text = "Place Order")
            }
        }

    }

}
