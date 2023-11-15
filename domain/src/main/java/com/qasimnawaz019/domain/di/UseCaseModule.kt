package com.qasimnawaz019.domain.di

import com.qasimnawaz019.domain.usecase.AddToCartDatabaseUseCase
import com.qasimnawaz019.domain.usecase.AddToFavouriteDatabaseUseCase
import com.qasimnawaz019.domain.usecase.CategoriesUseCase
import com.qasimnawaz019.domain.usecase.LoginUseCase
import com.qasimnawaz019.domain.usecase.ProductUseCase
import com.qasimnawaz019.domain.usecase.ProductsUseCase
import com.qasimnawaz019.domain.usecase.RemoveFavouriteDatabaseUseCase
import com.qasimnawaz019.domain.usecase.RemoveFromCartDatabaseUseCase
import org.koin.dsl.module

val useCaseModule = module {
    single { LoginUseCase(get(), get()) }
    single { CategoriesUseCase(get(), get()) }
    single { ProductsUseCase(get(), get()) }
    single { ProductUseCase(get(), get()) }
    single { AddToFavouriteDatabaseUseCase(get()) }
    single { RemoveFavouriteDatabaseUseCase(get()) }
    single { AddToCartDatabaseUseCase(get()) }
    single { RemoveFromCartDatabaseUseCase(get()) }
}