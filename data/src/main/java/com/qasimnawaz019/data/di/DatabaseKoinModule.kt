package com.qasimnawaz019.data.di

import androidx.room.Room
import com.qasimnawaz019.data.database.CartWaveDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseKoinModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            CartWaveDatabase::class.java,
            "cartwave-database"
        ).build()
    }
}