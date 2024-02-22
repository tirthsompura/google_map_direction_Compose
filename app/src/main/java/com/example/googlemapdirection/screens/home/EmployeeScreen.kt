package com.example.googlemapdirection.screens.home

import androidx.activity.compose.BackHandler
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.googlemapdirection.screens.LocationPermissionScreen
import com.example.googlemapdirection.screens.MapScreen
import com.example.googlemapdirection.screens.common.ConfirmationDialogBottomSheet
import com.example.googlemapdirection.R
import com.example.googlemapdirection.utils.FirebaseKeyConstants
import com.example.googlemapdirection.utils.FirebaseKeyConstants.AcceptReq
import com.example.googlemapdirection.utils.FirebaseKeyConstants.DefaultReq
import com.example.googlemapdirection.utils.FirebaseKeyConstants.EMP_ID
import com.example.googlemapdirection.utils.FirebaseKeyConstants.EMP_LAT
import com.example.googlemapdirection.utils.FirebaseKeyConstants.EMP_LONG
import com.example.googlemapdirection.utils.FirebaseKeyConstants.EMP_NAME
import com.example.googlemapdirection.utils.FirebaseKeyConstants.Request
import com.example.googlemapdirection.utils.SessionManagerClass
import com.example.googlemapdirection.utils.checkForPermission
import com.example.googlemapdirection.utils.showToast
import com.google.android.gms.maps.model.LatLng

@Composable
fun EmployeeScreen(
    sessionManagerClass: SessionManagerClass,
    location: MutableState<LatLng>,
    viewModel: HomeViewModel,
    openBottomSheetDialog: MutableState<Boolean>,
) {
    val context = LocalContext.current

    viewModel.data.value.forEach { (key, value) ->
        if (key == Request && value == FirebaseKeyConstants.SentRequest) {
            openBottomSheetDialog.value = true
        }
    }

    var hasLocationPermission by remember {
        mutableStateOf(checkForPermission(context = context))
    }

    fun onClickDismissDialog() {
        openBottomSheetDialog.value = false
    }

    BackHandler {
        if (openBottomSheetDialog.value) {
            onClickDismissDialog()
        }
    }

    if (openBottomSheetDialog.value) {
        ConfirmationDialogBottomSheet(
            title = "Accept the order",
            label = "Do you want to go to ride?",
            icon = painterResource(id = R.drawable.ic_blue_bell),
            onDismiss = {
                viewModel.updateDocumentEmployeeDetails(
                    "JFhEqMwqRZm9RGX9xuJc",
                    mapOf(
                        Request to DefaultReq,
                        EMP_LAT to "",
                        EMP_LONG to "",
                        EMP_NAME to "",
                        EMP_ID to "",
                    )
                )
                onClickDismissDialog()
            },
            onAccept = {
                showToast(context, "Order Accepted")
                viewModel.updateDocumentEmployeeDetails(
                    "JFhEqMwqRZm9RGX9xuJc",
                    mapOf(
                        Request to AcceptReq,
                        EMP_LAT to viewModel.empCurrentLat.value.toString(),
                        EMP_LONG to viewModel.empCurrentLong.value.toString(),
                        EMP_NAME to sessionManagerClass.loginUserData?.firstName.toString(),
                        EMP_ID to sessionManagerClass.loginUserData?.userId.toString(),
                    )
                )
                openBottomSheetDialog.value = false
            }
        )
    }

    Column {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 20.dp)
        ) {
            if (hasLocationPermission) {
                MapScreen(context, location, viewModel, openBottomSheetDialog)
            } else {
                LocationPermissionScreen {
                    hasLocationPermission = true
                }
            }
        }
    }
}
