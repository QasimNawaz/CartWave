package com.qasimnawaz019.cartwave.ui.screens.cart

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.qasimnawaz019.cartwave.ui.components.CartWaveSurface
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
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(
                    modifier = Modifier
                        .height(3.dp)
                        .width(70.dp)
                        .background(MaterialTheme.colorScheme.onSurfaceVariant)
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
                                color = MaterialTheme.colorScheme.onBackground,
                                text = "Subtotal"
                            )
                            Text(
                                modifier = Modifier.weight(1f),
                                fontWeight = FontWeight.Bold,
                                fontSize = MaterialTheme.typography.titleMedium.fontSize,
                                color = MaterialTheme.colorScheme.onBackground,
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
                                color = MaterialTheme.colorScheme.onBackground,
                                text = "Shipping"
                            )
                            Text(
                                modifier = Modifier.weight(1f),
                                fontWeight = FontWeight.Bold,
                                fontSize = MaterialTheme.typography.titleMedium.fontSize,
                                color = MaterialTheme.colorScheme.onBackground,
                                textAlign = TextAlign.End,
                                text = "$ 9"
                            )
                        }
                        DashedDivider(
                            color = MaterialTheme.colorScheme.onBackground,
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
                        color = MaterialTheme.colorScheme.onBackground,
                        text = "Total amount"
                    )
                    Text(
                        modifier = Modifier.weight(1f),
                        fontWeight = FontWeight.Bold,
                        fontSize = MaterialTheme.typography.titleMedium.fontSize,
                        color = MaterialTheme.colorScheme.onBackground,
                        textAlign = TextAlign.End,
                        text = "$ ${subTotal + 9}"
                    )
                }
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("Checkout"),
                    onClick = {

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
//            Surface(
//                modifier = Modifier
//                    .heightIn(min = 100.dp, max = 200.dp)
//                    .fillMaxSize()
//                    .background(MaterialTheme.colorScheme.surfaceVariant)
//                    .padding(20.dp)
//            ) {
//                ConstraintLayout(
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .background(MaterialTheme.colorScheme.surfaceVariant),
//                ) {
//                    val (topCenterBar, subTotalContainer, subTotalLabel, subTotal, shippingContainer, shippingLabel, shipping, totalAmountContainer, totalAmountLabel, totalAmount, checkoutButton) = createRefs()
//                    Spacer(modifier = Modifier
//                        .height(3.dp)
//                        .width(70.dp)
//                        .background(MaterialTheme.colorScheme.onSurfaceVariant)
//                        .constrainAs(topCenterBar) {
//                            start.linkTo(parent.start)
//                            end.linkTo(parent.end)
//                            top.linkTo(parent.top)
//                        })
//
//                    ConstraintLayout(modifier = Modifier
//                        .constrainAs(subTotalContainer) {
//                            top.linkTo(topCenterBar.bottom, 12.dp)
//                            end.linkTo(parent.end)
//                            start.linkTo(parent.start)
//                            width = Dimension.fillToConstraints
//                        }) {
//                        Text(
//                            modifier = Modifier
//                                .constrainAs(subTotalLabel) {
//                                    bottom.linkTo(parent.bottom)
//                                    top.linkTo(parent.top)
//                                    start.linkTo(parent.start)
//                                },
//                            fontSize = MaterialTheme.typography.titleMedium.fontSize,
//                            color = MaterialTheme.colorScheme.onBackground,
//                            text = "Subtotal"
//                        )
//                        Text(
//                            modifier = Modifier
//                                .constrainAs(subTotal) {
//                                    bottom.linkTo(parent.bottom)
//                                    top.linkTo(parent.top)
//                                    start.linkTo(parent.end)
//                                },
//                            fontWeight = FontWeight.Bold,
//                            fontSize = MaterialTheme.typography.titleMedium.fontSize,
//                            color = MaterialTheme.colorScheme.onBackground,
//                            text = "$93"
//                        )
//
//                        createHorizontalChain(
//                            subTotalLabel, subTotal,
//                            chainStyle = ChainStyle.SpreadInside
//                        )
//                    }
//
//                    ConstraintLayout(modifier = Modifier
//                        .constrainAs(shippingContainer) {
//                            top.linkTo(subTotalContainer.bottom, 12.dp)
//                            end.linkTo(parent.end)
//                            start.linkTo(parent.start)
//                            width = Dimension.fillToConstraints
//                        }) {
//                        Text(
//                            modifier = Modifier
//                                .constrainAs(shippingLabel) {
//                                    bottom.linkTo(parent.bottom)
//                                    top.linkTo(parent.top)
//                                    start.linkTo(parent.start)
//                                },
//                            fontSize = MaterialTheme.typography.titleMedium.fontSize,
//                            color = MaterialTheme.colorScheme.onBackground,
//                            text = "Subtotal"
//                        )
//                        Text(
//                            modifier = Modifier
//                                .constrainAs(shipping) {
//                                    bottom.linkTo(parent.bottom)
//                                    top.linkTo(parent.top)
//                                    start.linkTo(parent.end)
//                                },
//                            fontWeight = FontWeight.Bold,
//                            fontSize = MaterialTheme.typography.titleMedium.fontSize,
//                            color = MaterialTheme.colorScheme.onBackground,
//                            text = "$93"
//                        )
//
//                        createHorizontalChain(
//                            shippingLabel, shipping,
//                            chainStyle = ChainStyle.SpreadInside
//                        )
//                    }
//
//                    ConstraintLayout(modifier = Modifier
//                        .constrainAs(totalAmountContainer) {
//                            top.linkTo(shippingContainer.bottom, 12.dp)
//                            end.linkTo(parent.end)
//                            start.linkTo(parent.start)
//                            width = Dimension.fillToConstraints
//                        }) {
//                        Text(
//                            modifier = Modifier
//                                .constrainAs(totalAmountLabel) {
//                                    bottom.linkTo(parent.bottom)
//                                    top.linkTo(parent.top)
//                                    start.linkTo(parent.start)
//                                },
//                            fontSize = MaterialTheme.typography.titleMedium.fontSize,
//                            color = MaterialTheme.colorScheme.onBackground,
//                            text = "Subtotal"
//                        )
//                        Text(
//                            modifier = Modifier
//                                .constrainAs(totalAmount) {
//                                    bottom.linkTo(parent.bottom)
//                                    top.linkTo(parent.top)
//                                    start.linkTo(parent.end)
//                                },
//                            fontWeight = FontWeight.Bold,
//                            fontSize = MaterialTheme.typography.titleMedium.fontSize,
//                            color = MaterialTheme.colorScheme.onBackground,
//                            text = "$93"
//                        )
//
//                        createHorizontalChain(
//                            totalAmountLabel, totalAmount,
//                            chainStyle = ChainStyle.SpreadInside
//                        )
//                    }
//
//                }
//            }
//            Box(
//                modifier = Modifier
//                    .heightIn(min = 100.dp, max = 200.dp)
//                    .fillMaxSize()
//                    .background(MaterialTheme.colorScheme.surfaceVariant)
//                    .padding(20.dp),
//            ) {
//                Button(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .testTag("Checkout"),
//                    onClick = {
//
//                    },
//                    colors = ButtonDefaults.buttonColors(
//                        contentColor = MaterialTheme.colorScheme.onPrimary,
//                        backgroundColor = MaterialTheme.colorScheme.primary,
//                        disabledContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
//                        disabledBackgroundColor = MaterialTheme.colorScheme.surfaceVariant
//                    ),
//                ) {
//                    Text(text = "Checkout")
//                }
//            }
        }) {
        LazyColumn(modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 120.dp)
            .background(MaterialTheme.colorScheme.background), content = {
            items(items = productsResponse, key = { it.id!! }) { product ->
                CartItem(product, onCartQtyUpdate = {
                    viewModel.updateCartQty(it)
                })
            }
        })
    }
}
