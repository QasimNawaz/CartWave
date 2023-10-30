package com.qasimnawaz019.cartwave.di

import com.qasimnawaz019.cartwave.ui.screens.MainActivityViewModel
import com.qasimnawaz019.cartwave.ui.screens.auth.login.LoginScreenViewModel
import com.qasimnawaz019.cartwave.ui.screens.home.HomeScreenViewModel
import com.qasimnawaz019.cartwave.ui.screens.onboarding.OnBoardingViewModel
import com.qasimnawaz019.cartwave.ui.screens.splash.SplashScreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MainActivityViewModel(get(), get()) }
    viewModel { OnBoardingViewModel(get()) }
    viewModel { LoginScreenViewModel(get(), get()) }
    viewModel { SplashScreenViewModel(get()) }
    viewModel { HomeScreenViewModel(get(), get(), get()) }
}