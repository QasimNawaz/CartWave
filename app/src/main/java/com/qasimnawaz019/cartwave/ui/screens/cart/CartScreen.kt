package com.qasimnawaz019.cartwave.ui.screens.cart

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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetState
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.qasimnawaz019.cartwave.ui.screens.graphs.MainScreenInfo
import com.qasimnawaz019.cartwave.utils.DashedDivider
import com.qasimnawaz019.domain.model.Product
import org.koin.androidx.compose.getViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CartScreen(
    onNavigate: (route: String) -> Unit, viewModel: CartScreenViewModel = getViewModel()
) {

    val lazyListState = rememberLazyListState()

    val productsResponse: List<Product> by viewModel.cartProducts.collectAsStateWithLifecycle()
    val subTotal: Double by viewModel.subTotal.collectAsStateWithLifecycle()

    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = BottomSheetState(BottomSheetValue.Collapsed)
    )

    BottomSheetScaffold(modifier = Modifier.background(MaterialTheme.colorScheme.background),
        scaffoldState = scaffoldState,
        sheetShape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp),
        sheetElevation = 20.dp,
        sheetPeekHeight = 120.dp,
        backgroundColor = MaterialTheme.colorScheme.background,
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
                                text = "$ 9"
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
                        text = "$ ${subTotal + 9}"
                    )
                }
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("Checkout"),
                    onClick = {
                        onNavigate.invoke(MainScreenInfo.CheckOut.route)
                    },
                    colors = ButtonDefaults.buttonColors(
                        contentColor = MaterialTheme.colorScheme.onPrimary,
                        backgroundColor = MaterialTheme.colorScheme.primary,
                        disabledContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        disabledBackgroundColor = MaterialTheme.colorScheme.surfaceVariant
                    ),
                ) {
                    Text(text = "Checkout")
                }
            }
        }) {
        LazyColumn(state = lazyListState,
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 120.dp)
                .background(MaterialTheme.colorScheme.background),
            content = {
                items(items = productsResponse, key = { it.id!! }) { product ->
                    CartItem(product, onCartQtyUpdate = {
                        viewModel.updateCartQty(it)
                    })
                }
            })
    }
}
