package com.qasimnawaz019.cartwave.ui.screens.address

import android.util.Log
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.viewModelScope
import com.qasimnawaz019.cartwave.base.BaseViewModel
import com.qasimnawaz019.domain.model.Address
import com.qasimnawaz019.domain.usecase.address.AddAddressUseCase
import com.qasimnawaz019.domain.usecase.address.GetAddressesUseCase
import com.qasimnawaz019.domain.usecase.address.UpdatePrimaryAddressUseCase
import com.qasimnawaz019.domain.utils.NetworkCall
import com.qasimnawaz019.domain.utils.NetworkUiState
import kotlinx.coroutines.launch

class AddressScreenViewModel(
    private val addAddressUseCase: AddAddressUseCase,
    private val updatePrimaryAddressUseCase: UpdatePrimaryAddressUseCase,
    private val getAddressesUseCase: GetAddressesUseCase
) : BaseViewModel<List<Address>>() {

    init {
        getAddresses()
    }

    private fun getAddresses() {
        viewModelScope.launch {
            _networkUiState.emit(NetworkUiState.Loading)
            getAddressesUseCase.execute(Unit).asUiState()
        }
    }

    fun addAddress(address: String) {
        viewModelScope.launch {
            _networkUiState.emit(NetworkUiState.Loading)
            addAddressUseCase.execute(AddAddressUseCase.Params(address)).collect {
                when (it) {
                    is NetworkCall.Error.NoNetwork -> {
                        Log.d("asUiState", "ApiResponse.Error.NoNetwork Emit")
                        _networkUiState.emit(
                            NetworkUiState.Error(
                                code = 12029,
                                error = "${it.cause.message}"
                            )
                        )
                    }

                    is NetworkCall.Error.HttpError -> {
                        Log.d("asUiState", "ApiResponse.Error.HttpError Emit")
                        _networkUiState.emit(
                            NetworkUiState.Error(
                                code = it.code,
                                error = "${it.message}"
                            )
                        )
                    }

                    is NetworkCall.Error.SerializationError -> {
                        Log.d("asUiState", "ApiResponse.Error.SerializationError Emit")
                        _networkUiState.emit(NetworkUiState.Error(error = "${it.message}"))
                    }

                    is NetworkCall.Error.GenericError -> {
                        Log.d("asUiState", "ApiResponse.Error.GenericError Emit")
                        _networkUiState.emit(NetworkUiState.Error(error = "${it.message}"))
                    }

                    is NetworkCall.Success -> {
                        Log.d("asUiState", "ApiResponse.Success Emit")
                        if (it.data.success && it.data.data != null) {
                            getAddresses()
                        } else {
                            _networkUiState.emit(NetworkUiState.Error(error = it.data.message))
                        }
                    }
                }
            }
        }
    }

    fun updatePrimaryAddress(addressId: Int, addresses: SnapshotStateList<Address>) {
        viewModelScope.launch {
            updatePrimaryAddressUseCase.execute(UpdatePrimaryAddressUseCase.Params(addressId))
                .collect {
                    when (it) {
                        is NetworkCall.Error.NoNetwork -> {
                            Log.d("asUiState", "ApiResponse.Error.NoNetwork Emit")
                            _networkUiState.emit(
                                NetworkUiState.Error(
                                    code = 12029,
                                    error = "${it.cause.message}"
                                )
                            )
                        }

                        is NetworkCall.Error.HttpError -> {
                            Log.d("asUiState", "ApiResponse.Error.HttpError Emit")
                            _networkUiState.emit(
                                NetworkUiState.Error(
                                    code = it.code,
                                    error = "${it.message}"
                                )
                            )
                        }

                        is NetworkCall.Error.SerializationError -> {
                            Log.d("asUiState", "ApiResponse.Error.SerializationError Emit")
                            _networkUiState.emit(NetworkUiState.Error(error = "${it.message}"))
                        }

                        is NetworkCall.Error.GenericError -> {
                            Log.d("asUiState", "ApiResponse.Error.GenericError Emit")
                            _networkUiState.emit(NetworkUiState.Error(error = "${it.message}"))
                        }

                        is NetworkCall.Success -> {
                            Log.d("asUiState", "ApiResponse.Success Emit")
                            if (it.data.success && it.data.data != null) {
                                _networkUiState.emit(NetworkUiState.Success(addresses.map { address ->
                                    if (address.id == addressId) address.copy(
                                        isPrimary = true
                                    ) else address.copy(
                                        isPrimary = false
                                    )
                                }))
                            } else {
                                _networkUiState.emit(NetworkUiState.Error(error = it.data.message))
                            }
                        }
                    }
                }
        }
    }

}