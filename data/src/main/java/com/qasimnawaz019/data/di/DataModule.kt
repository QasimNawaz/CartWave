package com.qasimnawaz019.data.di

import org.koin.dsl.module

val dataModule = module {
    includes(remoteModule)
}