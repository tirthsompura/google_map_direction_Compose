package com.example.googlemapdirection.navigations

import com.example.googlemapdirection.utils.ConstantAppScreenName


sealed class AuthenticationScreens(val route: String) {

    object LoginScreen : AuthenticationScreens(ConstantAppScreenName.LOGIN_SCREEN)

    object RegisterScreen : AuthenticationScreens(ConstantAppScreenName.REGISTER_SCREEN)
}