package com.qasimnawaz019.data.repository.datastore

import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {
    suspend fun saveOnBoardingState(completed: Boolean)
    fun readOnBoardingState(): Flow<Boolean>

    suspend fun saveAccessToken(token: String)

    fun readAccessToken(): Flow<String?>

    suspend fun saveCategories(categories: Set<String>)

    fun readCategories(): Flow<Set<String>>
}