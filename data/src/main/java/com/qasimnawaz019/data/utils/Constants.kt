package com.qasimnawaz019.data.utils

import com.qasimnawaz019.data.BuildConfig

const val LOGIN = "${BuildConfig.BASE_URL}auth/login"
const val REGISTER = "${BuildConfig.BASE_URL}auth/register"
const val PRODUCTS_BY_CATEGORY = "${BuildConfig.BASE_URL}product/getProductsByCategory"
const val PRODUCTS_GROUP_BY_CATEGORY = "${BuildConfig.BASE_URL}product/getProductsGroupByCategory"
const val ADD_PRODUCT = "${BuildConfig.BASE_URL}product/addProduct"
const val GET_PRODUCT = "${BuildConfig.BASE_URL}product/getProduct"
const val GET_FAVOURITES = "${BuildConfig.BASE_URL}favourite/getFavourites"
const val ADD_TO_FAVOURITE = "${BuildConfig.BASE_URL}favourite/addToFavourite"
const val REMOVE_FROM_FAVOURITE = "${BuildConfig.BASE_URL}favourite/removeFromFavourite"
const val GET_USER_CART = "${BuildConfig.BASE_URL}cart/getUserCart"
const val ADD_TO_CART = "${BuildConfig.BASE_URL}cart/addToCart"
const val REMOVE_FROM_CART = "${BuildConfig.BASE_URL}cart/removeFromCart"
const val ADD_ADDRESS = "${BuildConfig.BASE_URL}address/addAddress"
const val UPDATE_PRIMARY_ADDRESS = "${BuildConfig.BASE_URL}address/updatePrimaryAddress"
const val GET_PRIMARY_ADDRESS = "${BuildConfig.BASE_URL}address/getPrimaryAddress"
const val GET_ADDRESSES = "${BuildConfig.BASE_URL}address/getAddress"
const val PLACE_ORDER = "${BuildConfig.BASE_URL}order/placeOrder"

const val FAVOURITE_PRODUCT_TABLE = "FAVOURITE_PRODUCT_TABLE"
const val MY_CART_TABLE = "MY_CART_TABLE"