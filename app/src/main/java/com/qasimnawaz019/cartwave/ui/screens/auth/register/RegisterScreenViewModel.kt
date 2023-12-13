package com.qasimnawaz019.cartwave.ui.screens.auth.register

import androidx.lifecycle.viewModelScope
import com.qasimnawaz019.cartwave.base.BaseViewModel
import com.qasimnawaz019.cartwave.utils.EmailState
import com.qasimnawaz019.cartwave.utils.NameState
import com.qasimnawaz019.cartwave.utils.PasswordState
import com.qasimnawaz019.data.repository.datastore.DataStoreRepository
import com.qasimnawaz019.domain.dto.login.RegisterRequestDto
import com.qasimnawaz019.domain.model.User
import com.qasimnawaz019.domain.usecase.auth.RegisterUseCase
import com.qasimnawaz019.domain.utils.NetworkUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RegisterScreenViewModel(
    private val useCase: RegisterUseCase,
    private val dataStoreRepository: DataStoreRepository
) :
    BaseViewModel<User>() {

    private val _firstNameState = NameState()
    val firstNameState = _firstNameState

    private val _lastNameState = NameState()
    val lastNameState = _lastNameState

    private val _emailState = EmailState()
    val emailState = _emailState

    private val _passwordState = PasswordState()
    val passwordState = _passwordState

    private val _redirectToHome = MutableStateFlow<Boolean>(false)
    val redirectToHome: StateFlow<Boolean> = _redirectToHome

    fun onFirstNameChange(name: String) {
        _firstNameState.text = name
    }

    fun onLastNameChange(name: String) {
        _lastNameState.text = name
    }

    fun onEmailChange(email: String) {
        _emailState.text = email
    }

    fun onPasswordChange(password: String) {
        _passwordState.text = password
    }

    fun performRegister(registerRequestDto: RegisterRequestDto) {
        viewModelScope.launch {
            _networkUiState.emit(NetworkUiState.Loading)
            useCase.execute(RegisterUseCase.Params(registerRequestDto)).asUiState()
        }
    }

    fun saveUser(user: User, remember: Boolean) {
        viewModelScope.launch {
            dataStoreRepository.saveRemember(remember)
            dataStoreRepository.saveUser(user)
            user.authToken?.let {
                dataStoreRepository.saveAccessToken(it)
            }
            _redirectToHome.value = true
        }
    }

    fun ifAllDataValid(): Boolean {
        return firstNameState.error == null && firstNameState.text.isNotBlank() && lastNameState.error == null && lastNameState.text.isNotBlank() && emailState.error == null && emailState.text.isNotBlank() && passwordState.error == null && passwordState.text.isNotBlank()
    }

    fun emptyUiState() {
        viewModelScope.launch {
            _networkUiState.emit(NetworkUiState.Empty)
        }
    }
}