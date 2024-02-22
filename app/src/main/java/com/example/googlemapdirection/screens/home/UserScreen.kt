package com.example.googlemapdirection.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.googlemapdirection.screens.LocationPermissionScreen
import com.example.googlemapdirection.screens.MapScreen
import com.example.googlemapdirection.utils.SessionManagerClass
import com.example.googlemapdirection.utils.checkForPermission
import com.google.android.gms.maps.model.LatLng

@Composable
fun UserScreen(
    sessionManagerClass: SessionManagerClass,
    location: MutableState<LatLng>,
    viewModel: HomeViewModel,
    openBottomSheetDialog: MutableState<Boolean>,
) {
    val context = LocalContext.current
    var hasLocationPermission by remember {
        mutableStateOf(checkForPermission(context = context))
    }

    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 20.dp)
        ) {
            if (hasLocationPermission) {
                MapScreen(
                    context,
                    location,
                    viewModel,
                    openBottomSheetDialog,
                )
            } else {
                LocationPermissionScreen {
                    hasLocationPermission = true
                }
            }
        }
    }
}