package com.example.googlemapdirection.navigations

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.googlemapdirection.App
import com.example.googlemapdirection.R
import com.example.googlemapdirection.screens.home.HomeScreen
import com.example.googlemapdirection.utils.AuthFirebase
import com.example.googlemapdirection.utils.RootGraph
import com.example.googlemapdirection.utils.SessionManagerClass
import kotlinx.coroutines.delay

@Composable
fun RootNavGraph(sessionManagerClass: SessionManagerClass) {

    var showSplashScreen by remember { mutableStateOf(true) }

    val navController = rememberNavController()
    App.navHostController = navController
    App.context = LocalContext.current

    LaunchedEffect(showSplashScreen) {
        delay(2000)
        showSplashScreen = false
    }
    val userData = remember { mutableStateOf(AuthFirebase.auth.currentUser) }

    Crossfade(targetState = showSplashScreen, label = "") { isSplashScreenVisible ->
        if (isSplashScreenVisible) {
            SplashScreen {
                showSplashScreen = false
            }
        } else {
            NavHost(
                navController = navController,
                route = RootGraph.ROOT,
                startDestination = if (userData.value == null) RootGraph.AUTHENTICATION else RootGraph.DASHBOARD,
            ) {
                authenticationNavigationGraph(navController = navController,userData)
                composable(route = RootGraph.DASHBOARD) {
                    HomeScreen(sessionManagerClass = sessionManagerClass,userData = userData,
                        navController = navController
                    )
                }
            }
        }
    }
}

@Composable
fun SplashScreen(navigateToAuthOrMainScreen: () -> Unit) {
    // Rotate effect for the image
    var rotationState by remember { mutableFloatStateOf(0f) }

    // Navigate to AuthOrMainScreen after a delay
    LaunchedEffect(true) {
        // Simulate a delay of 2 seconds
        delay(2000)
        // Call the provided lambda to navigate to AuthOrMainScreen
        navigateToAuthOrMainScreen()
    }

    // Rotation effect animation
    LaunchedEffect(rotationState) {
        while (true) {
            delay(16) // Adjust the delay to control the rotation speed
            rotationState += 1f
        }
    }

    // Splash screen UI with transitions
    val scale by animateFloatAsState(
        targetValue = 1f,
        animationSpec = TweenSpec(durationMillis = 500), label = ""
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_gps),
            contentDescription = null,
            modifier = Modifier
                .size(150.dp)
                .clip(CircleShape)
                .scale(scale)
                .rotate(rotationState) // Apply the rotation effect
        )
    }
}