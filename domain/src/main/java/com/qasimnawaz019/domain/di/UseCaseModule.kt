package com.qasimnawaz019.domain.di

import com.qasimnawaz019.domain.usecase.AddToCartUseCase
import com.qasimnawaz019.domain.usecase.AddToFavouriteUseCase
import com.qasimnawaz019.domain.usecase.FavouritesPagingUseCase
import com.qasimnawaz019.domain.usecase.GetUserCartUseCase
import com.qasimnawaz019.domain.usecase.ProductUseCase
import com.qasimnawaz019.domain.usecase.ProductsByCategoryUseCase
import com.qasimnawaz019.domain.usecase.ProductsGroupBySubCategoryUseCase
import com.qasimnawaz019.domain.usecase.RemoveFromCartUseCase
import com.qasimnawaz019.domain.usecase.RemoveFromFavouriteUseCase
import com.qasimnawaz019.domain.usecase.auth.LoginUseCase
import com.qasimnawaz019.domain.usecase.auth.RegisterUseCase
import org.koin.dsl.module

val useCaseModule = module {
    single { LoginUseCase(get(), get()) }
    single { RegisterUseCase(get(), get()) }
    single { ProductsByCategoryUseCase(get(), get()) }
    single { ProductsGroupBySubCategoryUseCase(get(), get()) }
    single { ProductUseCase(get(), get()) }
    single { AddToFavouriteUseCase(get(), get()) }
    single { FavouritesPagingUseCase(get(), get()) }
    single { RemoveFromFavouriteUseCase(get(), get()) }
    single { GetUserCartUseCase(get(), get()) }
    single { AddToCartUseCase(get(), get()) }
    single { RemoveFromCartUseCase(get(), get()) }
}