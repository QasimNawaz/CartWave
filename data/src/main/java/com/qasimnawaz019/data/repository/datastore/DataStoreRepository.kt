package com.qasimnawaz019.data.repository.datastore

import com.qasimnawaz019.domain.model.User
import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {
    suspend fun saveOnBoardingState(completed: Boolean)
    fun readOnBoardingState(): Flow<Boolean>

    suspend fun saveRemember(remember: Boolean)
    fun readRememberState(): Flow<Boolean>

    suspend fun saveAccessToken(token: String)

    fun readAccessToken(): Flow<String?>

    suspend fun saveUser(user: User)

    fun getUser(): Flow<User?>
}