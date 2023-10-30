package com.qasimnawaz019.data.di

import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

val dispatchersModule = module {
    single { Dispatchers.IO }
}