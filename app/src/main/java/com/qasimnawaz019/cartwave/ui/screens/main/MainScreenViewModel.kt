package com.qasimnawaz019.cartwave.ui.screens.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qasimnawaz019.data.repository.datastore.DataStoreRepository
import com.qasimnawaz019.domain.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainScreenViewModel(
    private val dataStoreRepository: DataStoreRepository,
) : ViewModel() {

    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> = _user.asStateFlow()

    init {
        getUser()
    }

    private fun getUser() {
        viewModelScope.launch {
            dataStoreRepository.getUser().collect {
                _user.emit(it)
            }
        }
    }

}