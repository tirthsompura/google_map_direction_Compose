package com.example.googlemapdirection.screens

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.googlemapdirection.R
import com.example.googlemapdirection.screens.home.HomeViewModel
import com.example.googlemapdirection.utils.FirebaseKeyConstants.AcceptReq
import com.example.googlemapdirection.utils.FirebaseKeyConstants.DeclineReq
import com.example.googlemapdirection.utils.FirebaseKeyConstants.DefaultReq
import com.example.googlemapdirection.utils.FirebaseKeyConstants.EMP_ID
import com.example.googlemapdirection.utils.FirebaseKeyConstants.EMP_LAT
import com.example.googlemapdirection.utils.FirebaseKeyConstants.EMP_LONG
import com.example.googlemapdirection.utils.FirebaseKeyConstants.EMP_NAME
import com.example.googlemapdirection.utils.FirebaseKeyConstants.Request
import com.example.googlemapdirection.utils.FirebaseKeyConstants.SentRequest
import com.example.googlemapdirection.utils.FirebaseKeyConstants.USER
import com.example.googlemapdirection.utils.FirebaseKeyConstants.USER_ID
import com.example.googlemapdirection.utils.FirebaseKeyConstants.USER_LET
import com.example.googlemapdirection.utils.FirebaseKeyConstants.USER_LONG
import com.example.googlemapdirection.utils.FirebaseKeyConstants.USER_NAME
import com.example.googlemapdirection.utils.bitmapDescriptor
import com.example.googlemapdirection.utils.capitaliseIt
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun MyMap(
    viewModel: HomeViewModel,
    context: Context,
    mapProperties: MapProperties,
    location: MutableState<LatLng>,
    onChangeMapType: (mapType: MapType) -> Unit,
) {
    val latLongList = remember {
        mutableStateListOf(location.value)
    }
    var mapTypeMenuExpanded by remember { mutableStateOf(false) }
    var mapTypeMenuSelectedText by remember {
        mutableStateOf(
            MapType.NORMAL.name.capitaliseIt()
        )
    }
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(location.value, 15f)
    }

    val isDefaultLocation = remember {
        mutableStateOf(true)
    }
    viewModel.data.value.forEach { (key, value) ->
        if (key == Request && value == AcceptReq) {
            isDefaultLocation.value = false
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        GoogleMap(modifier = Modifier.matchParentSize(),
            cameraPositionState = cameraPositionState,
            properties = mapProperties.copy(isMyLocationEnabled = isDefaultLocation.value),
            onMapClick = {
                viewModel.data.value.forEach { (key, value) ->
                    if ((key == Request && value == DeclineReq) || (key == Request && value == DefaultReq)) {
                        latLongList.add(it)
                    }
                }
            }) {
            val height = 100
            val width = 100
            val b = context.resources.getDrawable(R.drawable.car) as (BitmapDrawable)
            val smallMarker = Bitmap.createScaledBitmap(b.bitmap, width, height, false)

            val defaultIcon =
                context.resources.getDrawable(R.drawable.ic_user_location) as (BitmapDrawable)
            val smallMarkerDefault =
                Bitmap.createScaledBitmap(defaultIcon.bitmap, width, height, false)


            val latLongDataUser = remember {
                mutableStateOf(LatLng(0.0, 0.0))
            }

            val latLongDataEmp = remember {
                mutableStateOf(LatLng(0.0, 0.0))
            }
            viewModel.data.value.forEach { (key, value) ->
                if (key == Request && value == AcceptReq) {
                    val employeeLatKey = EMP_LAT
                    val empLatData = viewModel.data.value[employeeLatKey]

                    val employeeLongKey = EMP_LONG
                    val empLongData = viewModel.data.value[employeeLongKey]

                    latLongDataEmp.value = LatLng(
                        empLatData.toString().toDouble(), empLongData.toString().toDouble()
                    )

                    Marker(
                        state = MarkerState(position = latLongDataEmp.value),
                        title = "Location",
                        snippet = "Marker in current location",
                        icon = bitmapDescriptor(smallMarker)
                    )

                    val userLatKey = USER_LET
                    val userLatData = viewModel.data.value[userLatKey]

                    val userLongKey = USER_LONG
                    val userLongData = viewModel.data.value[userLongKey]

                    latLongDataUser.value = LatLng(
                        userLatData.toString().toDouble(), userLongData.toString().toDouble()
                    )

                    Marker(
                        state = MarkerState(position = latLongDataUser.value),
                        title = "Location",
                        snippet = "Marker in current location",
                        icon = bitmapDescriptor(smallMarkerDefault)
                    )

                }
            }

            viewModel.data.value.forEach { (key, value) ->
                if (key == Request && value == AcceptReq) {
                    if ((latLongDataEmp.value.latitude != 0.0) && (latLongDataUser.value.latitude != 0.0)) {
                        LaunchedEffect(Unit) {
                            viewModel.getRoutePoints()
                        }
                    }
                }
            }
            viewModel.data.value.forEach { (key, value) ->
                if (key == Request && value == AcceptReq) {
                    if (viewModel.polyLineUpdated.value) {
                        Polyline(
                            points = viewModel.polylineOptions.points,
                            onClick = {},
                            color = Color.Blue
                        )
                    }
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 4.dp, horizontal = 4.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.TopStart
            ) {
                Button(onClick = { mapTypeMenuExpanded = true }) {
                    Text(text = mapTypeMenuSelectedText)
                    Icon(
                        imageVector = Icons.Filled.ArrowDropDown,
                        contentDescription = "Dropdown arrow",
                        modifier = Modifier.size(ButtonDefaults.IconSize)
                    )
                }
                DropdownMenu(expanded = mapTypeMenuExpanded,
                    onDismissRequest = { mapTypeMenuExpanded = false }) {
                    MapType.entries.forEach {
                        val mapType = it.name.capitaliseIt()
                        DropdownMenuItem(text = {
                            Text(text = mapType)
                        }, onClick = {
                            onChangeMapType(it)
                            mapTypeMenuSelectedText = mapType
                            mapTypeMenuExpanded = false
                        })
                    }
                }
            }
            Box(
                modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.BottomStart
            ) {
                if (viewModel.sessionManagerClass.loginUserData?.userType == USER) {
                    if (viewModel.showMap.value) {
                        Button(modifier = Modifier, onClick = {
                            //Update Data
                            viewModel.updateDocumentEmployeeDetails(
                                "BziCbK8VC8OwYBcRMq9A", mapOf(
                                    Request to SentRequest,
                                    USER_LET to viewModel.userCurrentLat.value,
                                    USER_LONG to viewModel.userCurrentLong.value,
                                    USER_NAME to viewModel.sessionManagerClass.loginUserData?.firstName.toString(),
                                    USER_ID to viewModel.sessionManagerClass.loginUserData?.userId.toString(),
                                )
                            )
                        }) {
                            Text(text = stringResource(R.string.send_request))
                        }
                    }
                } else {
                    viewModel.data.value.forEach { (key, value) ->
                        if (key == Request && value == AcceptReq) {
                            Button(modifier = Modifier, onClick = {
                                viewModel.updateDocumentEmployeeDetails(
                                    "BziCbK8VC8OwYBcRMq9A", mapOf(
                                        Request to DefaultReq,
                                        USER_LET to "",
                                        USER_LONG to "",
                                        USER_NAME to "",
                                        USER_ID to "",
                                        EMP_ID to "",
                                        EMP_NAME to "",
                                        EMP_LAT to "",
                                        EMP_LONG to "",
                                    )
                                )
                            }) {
                                Text(text = "Complete Ride")
                            }
                        }
                    }
                }
            }
        }
    }
}
