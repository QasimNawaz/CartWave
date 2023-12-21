package com.qasimnawaz019.data.di

import com.qasimnawaz019.data.repository.dataSource.FavouritesPagingSourceFactory
import com.qasimnawaz019.data.repository.dataSource.RemoteDataSource
import com.qasimnawaz019.data.repository.dataSourceImpl.RemoteDataSourceImpl
import org.koin.dsl.module

/**
 * Remote module
 * This DI module will be responsible of providing remote data source dependencies
 * which need to be live as long as app is living
 * @constructor Create empty Network module
 */
val remoteModule = module {
    single<RemoteDataSource> { RemoteDataSourceImpl(get(), get(), get()) }
    factory { FavouritesPagingSourceFactory(get(), get(), get()) }
}