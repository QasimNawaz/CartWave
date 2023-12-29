package com.qasimnawaz019.cartwave.app

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.disk.DiskCache
import coil.memory.MemoryCache
import coil.request.CachePolicy
import coil.util.DebugLogger
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

class CartWaveApp : Application(), ImageLoaderFactory {

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

    override fun newImageLoader(): ImageLoader {
        return ImageLoader(this).newBuilder()
            .memoryCachePolicy(CachePolicy.ENABLED)
            .memoryCache {
                MemoryCache.Builder(this)
                    .maxSizePercent(0.1)
                    .strongReferencesEnabled(true)
                    .build()
            }
            .diskCachePolicy(CachePolicy.ENABLED)
            .diskCache {
                DiskCache.Builder()
                    .maxSizePercent(0.03)
                    .directory(cacheDir)
                    .build()
            }
            .logger(DebugLogger())
            .build()
    }
}