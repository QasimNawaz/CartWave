package com.qasimnawaz019.domain.repository

import androidx.paging.PagingData
import com.qasimnawaz019.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface ProductsRepo {
    fun getProducts(): Flow<PagingData<Product>>

}