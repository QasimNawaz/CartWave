package com.qasimnawaz019.cartwave.ui.screens.splash

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qasimnawaz019.data.repository.datastore.DataStoreRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SplashScreenViewModel(
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {

    private val _userLoggedInState = MutableStateFlow<Int>(-1)
    val userLoggedInState: StateFlow<Int> = _userLoggedInState
    fun isUserLoggedIn() {
        viewModelScope.launch {
            Log.d("isUserLoggedIn", "ViewModel")
            _userLoggedInState.emit(1)
//            dataStoreRepository.readAccessToken().collect {
//                Log.d("isUserLoggedIn", "$it")
//                _uiState.emit(UiState.Success(it != null))
//            }
        }
    }
}