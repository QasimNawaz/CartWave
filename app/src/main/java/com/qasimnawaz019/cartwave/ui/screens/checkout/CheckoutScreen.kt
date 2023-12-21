package com.qasimnawaz019.cartwave.ui.screens.checkout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.qasimnawaz019.domain.model.Product
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckOutScreen(
    navController: NavHostController, viewModel: CheckOutScreenViewModel = getViewModel()
) {
    val sheetState =
        rememberModalBottomSheetState(skipPartiallyExpanded = true, confirmValueChange = { false })
    var showBottomSheet by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    val productsResponse: List<Product> by viewModel.cartProducts.collectAsStateWithLifecycle()
    val totalAmount: Double by viewModel.totalAmount.collectAsStateWithLifecycle()

    if (showBottomSheet) {
        ModalBottomSheet(onDismissRequest = {
            showBottomSheet = false
        }, dragHandle = {

        }, sheetState = sheetState) {
            Button(onClick = {
                scope.launch { sheetState.hide() }.invokeOnCompletion {
                    if (!sheetState.isVisible) {
                        showBottomSheet = false
                    }
                }
            }) {
                Text("Hide bottom sheet")
            }
        }
    }
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            CheckoutAddress(navController)
        }
        item {
            CheckoutItems(productsResponse)
        }
        item {
            Column(modifier = Modifier.padding(bottom = 10.dp)) {
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
                        .testTag("Checkout"),
                    onClick = {
                        showBottomSheet = true
                    },
                    colors = ButtonDefaults.buttonColors(
                        contentColor = MaterialTheme.colorScheme.onPrimary,
                        backgroundColor = MaterialTheme.colorScheme.primary,
                        disabledContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        disabledBackgroundColor = MaterialTheme.colorScheme.surfaceVariant
                    ),
                ) {
                    Text(text = "Checkout Now")
                }
            }
        }
    }
}
