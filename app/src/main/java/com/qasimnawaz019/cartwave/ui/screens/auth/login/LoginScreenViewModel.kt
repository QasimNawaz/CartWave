package com.qasimnawaz019.cartwave.ui.screens.auth.login

import androidx.lifecycle.viewModelScope
import com.qasimnawaz019.cartwave.base.BaseViewModel
import com.qasimnawaz019.cartwave.utils.PasswordState
import com.qasimnawaz019.cartwave.utils.UsernameState
import com.qasimnawaz019.data.repository.datastore.DataStoreRepository
import com.qasimnawaz019.domain.dto.login.LoginRequestDto
import com.qasimnawaz019.domain.model.LoginResponse
import com.qasimnawaz019.domain.usecase.LoginUseCase
import com.qasimnawaz019.domain.utils.NetworkUiState
import kotlinx.coroutines.launch

class LoginScreenViewModel(
    private val useCase: LoginUseCase, private val dataStoreRepository: DataStoreRepository
) : BaseViewModel<LoginResponse>() {

    private val _usernameState = UsernameState()
    val usernameState = _usernameState

    private val _passwordState = PasswordState()
    val passwordState = _passwordState

    fun onUsernameChange(username: String) {
        _usernameState.text = username
    }

    fun onPasswordChange(password: String) {
        _passwordState.text = password
    }

    fun performLogin(loginRequestDto: LoginRequestDto) {
        viewModelScope.launch {
            _networkUiState.emit(NetworkUiState.Loading)
            useCase.execute(LoginUseCase.Params(loginRequestDto)).asUiState()
        }
    }

    fun saveToken(token: String) {
        viewModelScope.launch {
            dataStoreRepository.saveAccessToken(token)
        }
    }

    fun emptyUiState() {
        viewModelScope.launch {
            _networkUiState.emit(NetworkUiState.Empty)
        }
    }
}
