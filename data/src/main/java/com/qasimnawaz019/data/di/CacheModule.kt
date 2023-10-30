package com.qasimnawaz019.data.di

import com.qasimnawaz019.data.repository.datastore.DataStoreRepository
import com.qasimnawaz019.data.repository.datastore.DataStoreRepositoryImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

/**
 * Cache module
 * This DI module will be responsible of providing caching dependencies
 * which need to be live as long as app is living
 * @constructor Create empty Cache module
 */
val cacheModule = module {
    single<DataStoreRepository> { DataStoreRepositoryImpl(androidContext()) }
}