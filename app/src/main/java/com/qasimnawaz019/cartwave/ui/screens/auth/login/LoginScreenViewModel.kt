package com.qasimnawaz019.cartwave.ui.screens.auth.login

import androidx.lifecycle.viewModelScope
import com.qasimnawaz019.cartwave.base.BaseViewModel
import com.qasimnawaz019.cartwave.utils.EmailState
import com.qasimnawaz019.cartwave.utils.PasswordState
import com.qasimnawaz019.data.repository.datastore.DataStoreRepository
import com.qasimnawaz019.domain.dto.login.LoginRequestDto
import com.qasimnawaz019.domain.model.User
import com.qasimnawaz019.domain.usecase.auth.LoginUseCase
import com.qasimnawaz019.domain.utils.NetworkUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginScreenViewModel(
    private val useCase: LoginUseCase, private val dataStoreRepository: DataStoreRepository
) : BaseViewModel<User>() {

    private val _emailState = EmailState()
    val emailState = _emailState

    private val _passwordState = PasswordState()
    val passwordState = _passwordState

    private val _redirectToHome = MutableStateFlow<Boolean>(false)
    val redirectToHome: StateFlow<Boolean> = _redirectToHome

    fun onEmailChange(username: String) {
        _emailState.text = username
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

    fun saveUser(user: User, remember: Boolean) {
        viewModelScope.launch {
            user.authToken?.let {
                dataStoreRepository.saveAccessToken(it)
            }
            dataStoreRepository.saveRemember(remember)
            dataStoreRepository.saveUser(user)
            _redirectToHome.value = true
        }
    }

    fun ifAllDataValid(): Boolean {
        return emailState.error == null && emailState.text.isNotBlank() && passwordState.error == null && passwordState.text.isNotBlank()
    }

    fun emptyUiState() {
        viewModelScope.launch {
            _networkUiState.emit(NetworkUiState.Empty)
        }
    }
}
