package com.qasimnawaz019.cartwave.di

import com.qasimnawaz019.cartwave.ui.screens.MainActivityViewModel
import com.qasimnawaz019.cartwave.ui.screens.address.AddressScreenViewModel
import com.qasimnawaz019.cartwave.ui.screens.auth.login.LoginScreenViewModel
import com.qasimnawaz019.cartwave.ui.screens.auth.register.RegisterScreenViewModel
import com.qasimnawaz019.cartwave.ui.screens.cart.CartScreenViewModel
import com.qasimnawaz019.cartwave.ui.screens.checkout.CheckOutScreenViewModel
import com.qasimnawaz019.cartwave.ui.screens.detail.ProductDetailViewModel
import com.qasimnawaz019.cartwave.ui.screens.home.HomeScreenViewModel
import com.qasimnawaz019.cartwave.ui.screens.main.MainScreenViewModel
import com.qasimnawaz019.cartwave.ui.screens.onboarding.OnBoardingViewModel
import com.qasimnawaz019.cartwave.ui.screens.splash.SplashScreenViewModel
import com.qasimnawaz019.cartwave.ui.screens.wishlist.WishlistScreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MainActivityViewModel(get()) }
    viewModel { MainScreenViewModel(get()) }
    viewModel { OnBoardingViewModel(get()) }
    viewModel { LoginScreenViewModel(get(), get()) }
    viewModel { RegisterScreenViewModel(get(), get()) }
    viewModel { SplashScreenViewModel(get(), get()) }
    viewModel { HomeScreenViewModel(get(), get(), get(), get(), get()) }
    viewModel { ProductDetailViewModel(get(), get(), get(), get(), get(), get()) }
    viewModel { WishlistScreenViewModel(get(), get()) }
    viewModel { CartScreenViewModel(get(), get(), get()) }
    viewModel { CheckOutScreenViewModel(get(), get(), get()) }
    viewModel { AddressScreenViewModel(get(), get(), get()) }
}