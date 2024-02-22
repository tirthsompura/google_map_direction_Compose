package com.example.googlemapdirection.navigations

import androidx.compose.runtime.MutableState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.googlemapdirection.screens.authentications.login.LoginScreen
import com.example.googlemapdirection.screens.authentications.signup.SignUpScreen
import com.example.googlemapdirection.utils.RootGraph
import com.google.firebase.auth.FirebaseUser


fun NavGraphBuilder.authenticationNavigationGraph(
    navController: NavHostController,
    userData: MutableState<FirebaseUser?>,
) {
    navigation(
        route = RootGraph.AUTHENTICATION, startDestination = AuthenticationScreens.LoginScreen.route
    ) {
        composable(
            route = AuthenticationScreens.LoginScreen.route,
        ) {
            LoginScreen(navController = navController)
        }

        composable(route = AuthenticationScreens.RegisterScreen.route) {
            SignUpScreen(
                navController = navController, userData = userData
            )
        }
    }
}