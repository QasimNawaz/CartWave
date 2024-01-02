package com.qasimnawaz019.cartwave.ui.screens.address

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.qasimnawaz019.cartwave.R
import com.qasimnawaz019.cartwave.ui.components.AddressDialog
import com.qasimnawaz019.cartwave.ui.components.CartWaveScaffold
import com.qasimnawaz019.cartwave.ui.components.EmptyView
import com.qasimnawaz019.cartwave.ui.components.LoadingDialog
import com.qasimnawaz019.cartwave.ui.components.AddressItemShimmer
import com.qasimnawaz019.domain.model.Address
import com.qasimnawaz019.domain.utils.NetworkUiState
import org.koin.androidx.compose.getViewModel

@Composable
fun AddressScreen(
    navController: NavHostController, viewModel: AddressScreenViewModel = getViewModel()
) {

    val addressDialog = remember {
        mutableStateOf(false)
    }
    val listLoading = remember {
        mutableStateOf(false)
    }
    val addressLoading = remember {
        mutableStateOf(false)
    }
    val error = remember {
        mutableStateOf<String?>(null)
    }
    val addresses = remember {
        mutableStateListOf<Address>()
    }
    val primaryAddress = remember {
        mutableStateOf<Address?>(null)
    }
    val addressesResponse: NetworkUiState<List<Address>> by viewModel.networkUiState.collectAsStateWithLifecycle()

    LaunchedEffect(addressesResponse) {
        when (addressesResponse) {
            is NetworkUiState.Loading -> {
                listLoading.value = true
            }

            is NetworkUiState.Error -> {
                addressLoading.value = false
                listLoading.value = false
                (addressesResponse as NetworkUiState.Error).error.let {
                    error.value = it
                }
            }

            is NetworkUiState.Success -> {
                addressLoading.value = false
                listLoading.value = false
                (addressesResponse as NetworkUiState.Success<List<Address>>).data.let { list ->
                    primaryAddress.value = list.find { it.isPrimary }
                    addresses.clear()
                    addresses.addAll(list)
                }
            }

            else -> {}
        }
    }

    if (addressLoading.value) {
        LoadingDialog()
    }

    if (addressDialog.value) {
        AddressDialog(onDismissRequest = { addressDialog.value = false }, onAddAddress = {
            addressDialog.value = false
            viewModel.addAddress(it)
            addressLoading.value = true
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
                    .clickable {
//                        navController.previousBackStackEntry?.savedStateHandle?.set(
//                            "primaryAddress",
//                            primaryAddress.value
//                        )
                        navController.popBackStack()
                    },
                painter = painterResource(id = R.drawable.ic_arrow_left),
                contentDescription = null,
            )

            Text(
                modifier = Modifier.align(Alignment.Center),
                text = "Address",
                fontSize = MaterialTheme.typography.titleMedium.fontSize,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontWeight = FontWeight.Bold
            )
        }
    }) {
        if (error.value != null) {
            EmptyView(
                R.drawable.ic_network_error, error.value ?: "Something went wrong"
            )
        } else {
            Column(modifier = Modifier.padding(15.dp)) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    item {
                        Text(
                            text = "Choose you Location",
                            fontSize = MaterialTheme.typography.titleMedium.fontSize,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    item {
                        Text(
                            text = "Let's find your unforgettable event. Choose a location below to get started.",
                            fontSize = MaterialTheme.typography.titleMedium.fontSize,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }
                    item {
                        AddAddressView {
                            addressDialog.value = true
                        }
                    }
                    if (listLoading.value) {
                        item {
                            AddressItemShimmer()
                        }
                    }
                    items(items = addresses, key = {
                        it.id
                    }) {
                        Row(modifier = Modifier
                            .fillMaxWidth()
                            .border(
                                width = 1.dp,
                                color = if (it.isPrimary) Color.Green else Color.Gray.copy(
                                    alpha = 0.5f
                                ),
                                shape = RoundedCornerShape(10.dp)
                            )
                            .clip(shape = RoundedCornerShape(10.dp))
                            .clickable {
                                viewModel.updatePrimaryAddress(it.id, addresses)
                                addressLoading.value = true
                            }
                            .padding(vertical = 12.dp, horizontal = 6.dp),
                            verticalAlignment = Alignment.CenterVertically) {
                            OutlinedCard(
                                border = if (it.isPrimary) BorderStroke(
                                    width = 1.dp, color = Color.Green
                                ) else BorderStroke(
                                    width = 1.dp, color = Color.Gray.copy(
                                        alpha = 0.5f
                                    )
                                ),
                                modifier = Modifier.size(50.dp),
                                shape = CircleShape,
                                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                            ) {
                                AsyncImage(
                                    modifier = Modifier.fillMaxSize(),
                                    model = R.drawable.ic_map,
                                    contentDescription = null,
                                    contentScale = ContentScale.Fit
                                )
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                modifier = Modifier.weight(1f),
                                text = it.address,
                                fontSize = MaterialTheme.typography.titleSmall.fontSize,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                            )
                        }
                    }
                }

                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("AddressConfirm"),
                    onClick = {
//                        navController.previousBackStackEntry?.savedStateHandle?.set(
//                            "primaryAddress",
//                            primaryAddress.value
//                        )
                        navController.popBackStack()
                    },
                    colors = ButtonDefaults.buttonColors(
                        contentColor = MaterialTheme.colorScheme.onPrimary,
                        backgroundColor = MaterialTheme.colorScheme.primary,
                        disabledContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        disabledBackgroundColor = MaterialTheme.colorScheme.surfaceVariant
                    ),
                    enabled = !addressLoading.value && !listLoading.value && primaryAddress.value != null
                ) {
                    Text(text = "Confirm")
                }
            }
        }
    }

}