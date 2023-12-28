package com.qasimnawaz019.cartwave.app

import android.app.Application
import com.qasimnawaz019.cartwave.di.viewModelModule
import com.qasimnawaz019.data.di.appModule
import com.qasimnawaz019.data.di.cacheModule
import com.qasimnawaz019.data.di.dataModule
import com.qasimnawaz019.data.di.dispatchersModule
import com.qasimnawaz019.data.di.repositoryModule
import com.qasimnawaz019.domain.di.connectivityModule
import com.qasimnawaz019.domain.di.useCaseModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class CartWaveApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@CartWaveApp)
            modules(
                appModule,
                cacheModule,
                dispatchersModule,
                connectivityModule,
                dataModule,
                repositoryModule,
                useCaseModule,
                viewModelModule
            )
        }
    }
}