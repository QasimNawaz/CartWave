package com.qasimnawaz019.domain.di

import com.qasimnawaz019.domain.usecase.address.AddAddressUseCase
import com.qasimnawaz019.domain.usecase.address.GetAddressesUseCase
import com.qasimnawaz019.domain.usecase.address.GetPrimaryAddressUseCase
import com.qasimnawaz019.domain.usecase.address.UpdatePrimaryAddressUseCase
import com.qasimnawaz019.domain.usecase.auth.LoginUseCase
import com.qasimnawaz019.domain.usecase.auth.RegisterUseCase
import com.qasimnawaz019.domain.usecase.cart.AddToCartUseCase
import com.qasimnawaz019.domain.usecase.cart.GetUserCartUseCase
import com.qasimnawaz019.domain.usecase.cart.RemoveFromCartUseCase
import com.qasimnawaz019.domain.usecase.favourite.AddToFavouriteUseCase
import com.qasimnawaz019.domain.usecase.favourite.FavouritesPagingUseCase
import com.qasimnawaz019.domain.usecase.favourite.RemoveFromFavouriteUseCase
import com.qasimnawaz019.domain.usecase.order.PlaceOrderUseCase
import com.qasimnawaz019.domain.usecase.product.ProductDetailUseCase
import com.qasimnawaz019.domain.usecase.product.ProductsByCategoryUseCase
import com.qasimnawaz019.domain.usecase.product.ProductsGroupBySubCategoryUseCase
import org.koin.dsl.module

val useCaseModule = module {
    single { LoginUseCase(get(), get()) }
    single { RegisterUseCase(get(), get()) }
    single { ProductsByCategoryUseCase(get(), get()) }
    single { ProductsGroupBySubCategoryUseCase(get(), get()) }
    single { ProductDetailUseCase(get(), get()) }
    single { AddToFavouriteUseCase(get(), get()) }
    single { FavouritesPagingUseCase(get(), get()) }
    single { RemoveFromFavouriteUseCase(get(), get()) }
    single { GetUserCartUseCase(get(), get()) }
    single { AddToCartUseCase(get(), get()) }
    single { RemoveFromCartUseCase(get(), get()) }
    single { AddAddressUseCase(get(), get()) }
    single { UpdatePrimaryAddressUseCase(get(), get()) }
    single { GetAddressesUseCase(get(), get()) }
    single { GetPrimaryAddressUseCase(get(), get()) }
    single { PlaceOrderUseCase(get(), get()) }
}