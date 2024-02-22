package com.example.googlemapdirection.screens.home

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.firebasechatdemo.core.LocationReceiver
import com.example.googlemapdirection.navigations.AuthenticationScreens
import com.example.googlemapdirection.screens.common.HomeScreenTopAppBar
import com.example.googlemapdirection.ui.theme.tertiaryColor
import com.example.googlemapdirection.utils.AuthFirebase
import com.example.googlemapdirection.utils.FirebaseKeyConstants
import com.example.googlemapdirection.utils.FirebaseKeyConstants.EMPLOYEE
import com.example.googlemapdirection.utils.FirebaseKeyConstants.EMP_ID
import com.example.googlemapdirection.utils.FirebaseKeyConstants.EMP_LAT
import com.example.googlemapdirection.utils.FirebaseKeyConstants.EMP_LONG
import com.example.googlemapdirection.utils.FirebaseKeyConstants.EMP_NAME
import com.example.googlemapdirection.utils.FirebaseKeyConstants.USER
import com.example.googlemapdirection.utils.FirebaseKeyConstants.USER_LET
import com.example.googlemapdirection.utils.FirebaseKeyConstants.USER_LONG
import com.example.googlemapdirection.utils.SessionManagerClass
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.auth.FirebaseUser

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    userData: MutableState<FirebaseUser?>,
    sessionManagerClass: SessionManagerClass,
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val auth = AuthFirebase.auth

    val context = LocalContext.current

    val location = remember {
        mutableStateOf(
            LatLng(0.0, 0.0)
        )
    }

    LaunchedEffect(Unit) {
        val locationReceiver = LocationReceiver(context) {
            //Store Employee Lat-Long Data
            if (sessionManagerClass.loginUserData?.userType == EMPLOYEE) {
                location.value = LatLng(it.latitude, it.longitude)

                val sessionUserData = sessionManagerClass.loginUserData
                sessionUserData?.empLat = it.latitude
                sessionUserData?.empLong = it.longitude
                sessionManagerClass.loginUserData = sessionUserData
            }

            //Store User Lat-Long Data
            if (sessionManagerClass.loginUserData?.userType == USER) {
                location.value = LatLng(it.latitude, it.longitude)

                val sessionUserData = sessionManagerClass.loginUserData
                sessionUserData?.userLat = it.latitude
                sessionUserData?.userLong = it.longitude
                sessionManagerClass.loginUserData = sessionUserData
            }

            val updatedLocation = if (sessionManagerClass.loginUserData?.userType == USER) {
                mapOf(
                    USER_LET to it.latitude.toString(),
                    USER_LONG to it.longitude.toString(),
                )
            } else {
                mapOf(
                    EMP_LAT to it.latitude.toString(),
                    EMP_LONG to it.longitude.toString(),
                )
            }

            viewModel.data.value.forEach { (key, value) ->
                if (key == FirebaseKeyConstants.Request && value == FirebaseKeyConstants.AcceptReq) {
                    viewModel.updateDocumentEmployeeDetails(
                        "JFhEqMwqRZm9RGX9xuJc", updatedLocation
                    )
                }
            }

            viewModel.showMap.value = true
        }
        locationReceiver.startLocationUpdates()
    }

    val openBottomSheetDialog = remember {
        mutableStateOf(false)
    }

    Scaffold(
        topBar = {
            HomeScreenTopAppBar(sessionManagerClass){
                auth.signOut()
                userData.value = null
                viewModel.sessionManagerClass.clearSessionData()
                viewModel.updateDocumentEmployeeDetails(
                    "JFhEqMwqRZm9RGX9xuJc",
                    mapOf(
                        FirebaseKeyConstants.Request to FirebaseKeyConstants.DefaultReq,
                        EMP_LAT to "",
                        EMP_LONG to "",
                        EMP_NAME to "",
                        EMP_ID to "",
                    )
                )
                navController.navigate(AuthenticationScreens.LoginScreen.route)
            }
        },
        modifier = Modifier.fillMaxSize(),
        containerColor = tertiaryColor,
    ) {
        Column(
            modifier = Modifier
                .padding(top = 40.dp)
                .fillMaxSize()
                .background(Color.White),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (sessionManagerClass.loginUserData?.userType == USER) {
                UserScreen(sessionManagerClass, location, viewModel, openBottomSheetDialog)
            } else {
                EmployeeScreen(
                    sessionManagerClass, location, viewModel, openBottomSheetDialog)
            }
        }
    }
}