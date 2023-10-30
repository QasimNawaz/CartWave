package com.qasimnawaz019.domain.utils

sealed interface NetworkUiState<out T> {
    data class Success<T>(val data: T) : NetworkUiState<T>
    data class Error(val code: Int = -1, val error: String) : NetworkUiState<Nothing>
    object Loading : NetworkUiState<Nothing>

    object Empty : NetworkUiState<Nothing>
}