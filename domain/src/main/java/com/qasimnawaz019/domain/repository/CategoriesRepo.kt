package com.qasimnawaz019.domain.repository

import com.qasimnawaz019.domain.utils.ApiResponse
import kotlinx.coroutines.flow.Flow

interface CategoriesRepo {
    suspend fun getCategories(): Flow<ApiResponse<List<String>>>
}