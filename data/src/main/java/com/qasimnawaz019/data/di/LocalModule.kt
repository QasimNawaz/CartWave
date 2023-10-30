package com.qasimnawaz019.data.di

import com.qasimnawaz019.data.repository.dataSource.LocalDataSource
import com.qasimnawaz019.data.repository.dataSourceImpl.LocalDataSourceImpl
import org.koin.dsl.module

/**
 * Local module
 * This DI module will be responsible of providing local data source dependencies
 * which need to be live as long as app is living
 * @constructor Create empty Network module
 */
val localModule = module {
    single<LocalDataSource> { LocalDataSourceImpl(get()) }
}