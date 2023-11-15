package com.qasimnawaz019.data.di

import com.qasimnawaz019.data.database.CartWaveDatabase
import org.koin.dsl.module

val daosKoinModule = module {
    includes(databaseKoinModule)

    single { get<CartWaveDatabase>().favouriteProductsDao() }
    single { get<CartWaveDatabase>().myCartDao() }
}