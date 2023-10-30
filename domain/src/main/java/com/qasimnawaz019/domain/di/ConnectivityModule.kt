package com.qasimnawaz019.domain.di

import com.qasimnawaz019.domain.utils.Network
import com.qasimnawaz019.domain.utils.NetworkConnectivity
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

/**
 * Connectivity module
 * This DI module will be responsible of providing network check dependencies
 * which need to be live as long as app is living
 * @constructor Create empty Cache module
 */
val connectivityModule = module {
    single<NetworkConnectivity> { Network(androidContext()) }
}