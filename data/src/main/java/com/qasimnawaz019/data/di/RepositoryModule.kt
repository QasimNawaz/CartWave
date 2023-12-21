package com.qasimnawaz019.data.di

import com.qasimnawaz019.data.repository.repoImpl.AuthRepoImpl
import com.qasimnawaz019.data.repository.repoImpl.FavouriteProductsRepoImpl
import com.qasimnawaz019.data.repository.repoImpl.ProductsRepoImpl
import com.qasimnawaz019.data.repository.repoImpl.UserCartRepoImpl
import com.qasimnawaz019.domain.repository.AuthRepo
import com.qasimnawaz019.domain.repository.FavouriteProductsRepo
import com.qasimnawaz019.domain.repository.ProductsRepo
import com.qasimnawaz019.domain.repository.UserCartRepo
import org.koin.dsl.module

/**
 * Repositories module
 * This DI module will be responsible of providing repositories dependencies
 * which need to be live as long as app is living
 * @constructor Create empty Repositories module
 */
val repositoryModule = module {
    single<AuthRepo> { AuthRepoImpl(get(), get()) }
    single<ProductsRepo> { ProductsRepoImpl(get(), get()) }
    single<FavouriteProductsRepo> { FavouriteProductsRepoImpl(get(), get(), get()) }
    single<UserCartRepo> { UserCartRepoImpl(get(), get()) }
}