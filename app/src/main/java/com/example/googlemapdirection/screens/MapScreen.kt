package com.example.googlemapdirection.screens

import android.content.Context
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.googlemapdirection.screens.MyMap
import com.example.googlemapdirection.screens.home.HomeViewModel
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.MapProperties

@Composable
fun MapScreen(
    context: Context,
    location: MutableState<LatLng>,
    viewModel: HomeViewModel,
    openBottomSheetDialog: MutableState<Boolean>,
) {
    var mapProperties by remember { mutableStateOf(MapProperties()) }

    if (viewModel.showMap.value) {
        MyMap(
            viewModel,
            context = context,
            location = location,
            mapProperties = mapProperties,
            onChangeMapType = {
                mapProperties = mapProperties.copy(mapType = it)
            }
        )
    } else {
        Text(text = "Loading Map...")
    }
}