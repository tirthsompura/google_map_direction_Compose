package com.example.googlemapdirection.screens.home

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.googlemapdirection.response.CoordinatesRequest
import com.example.googlemapdirection.response.CoordinatesResponse
import com.example.googlemapdirection.usecase.GoogleCoordinatesUseCase
import com.example.googlemapdirection.usecase.Resource
import com.example.googlemapdirection.usecase.StateHandler
import com.example.googlemapdirection.utils.FirebaseKeyConstants
import com.example.googlemapdirection.utils.FirebaseKeyConstants.EmployeeLocation
import com.example.googlemapdirection.utils.FirebaseKeyConstants.Users
import com.example.googlemapdirection.utils.SessionManagerClass
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PolylineOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.maps.android.PolyUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    val googleCoordinatesUseCase: GoogleCoordinatesUseCase,
    val sessionManagerClass: SessionManagerClass,
) : ViewModel() {
    val empCurrentLat = mutableStateOf(0.0)
    val empCurrentLong = mutableStateOf(0.0)

    val userCurrentLat = mutableStateOf(0.0)
    val userCurrentLong = mutableStateOf(0.0)

    val showMap = mutableStateOf(false)

    private val _googleCoordinatesResponseDetailsState =
        MutableStateFlow(StateHandler<CoordinatesResponse>())
    val googleCoordinatesResponseDetailsState: StateFlow<StateHandler<CoordinatesResponse>> =
        _googleCoordinatesResponseDetailsState

    val polylineOptions = PolylineOptions()
    val polyLineUpdated = mutableStateOf(false)

    fun googleCoordinatesCall(request: CoordinatesRequest) {
        viewModelScope.launch {
            googleCoordinatesUseCase.execute(request = request).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        _googleCoordinatesResponseDetailsState.value =
                            StateHandler(data = result.data)

                        val polyline = result.data?.routes?.let {
                            PolyUtil.decode(it[0].geometry)
                        }

                        if (polyline != null) {
                            for (point in polyline) {
                                polylineOptions.add(LatLng(point.latitude, point.longitude))
                                Log.d("PloyLine","LAT${point.latitude},LONG:${point.longitude}")
                            }
                            polyLineUpdated.value = true
                        }

                    }

                    is Resource.Error -> {
                        _googleCoordinatesResponseDetailsState.value =
                            StateHandler(error = result.message)
                    }

                    is Resource.Loading -> {
                        _googleCoordinatesResponseDetailsState.value =
                            StateHandler(isLoading = true)
                    }
                }
            }
        }
    }


    private val db = FirebaseFirestore.getInstance()
    val data: MutableState<Map<String, Any>> = mutableStateOf(emptyMap())

    init {
        // Set up Firestore snapshot listener
        db.collection(EmployeeLocation)
            .document("JFhEqMwqRZm9RGX9xuJc")
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    // Handle error
                    return@addSnapshotListener
                }

                val documentData = snapshot?.data ?: emptyMap()
                // Update the state with new data
                data.value = documentData
            }
    }


    //Update Employee Details
    fun updateDocumentEmployeeDetails(documentId: String, newData: Map<String, Any>) {
        db.collection(EmployeeLocation).document(documentId)
            .update(newData)
            .addOnSuccessListener {
                // Handle success if needed
            }
            .addOnFailureListener { e ->
                // Handle failure
            }
    }

    fun updateDocumentUserDetails(documentId: String, newData: Map<String, Any>) {
        db.collection(Users).document(documentId)
            .update(newData)
            .addOnSuccessListener {
                // Handle success if needed
            }
            .addOnFailureListener { e ->
                // Handle failure
            }
    }

    fun getRoutePoints() {
        val empLatData = data.value[FirebaseKeyConstants.EMP_LAT]
        val empLongData = data.value[FirebaseKeyConstants.EMP_LONG]

        val employeeCoordinate = listOf(
            empLongData.toString().toDouble(),
            empLatData.toString().toDouble()
        )

        val userLatData = data.value[FirebaseKeyConstants.USER_LET]
        val userLongData = data.value[FirebaseKeyConstants.USER_LONG]
        val userCoordinate = listOf(
            userLongData.toString().toDouble(),
            userLatData.toString().toDouble()
        )

        googleCoordinatesCall(
            CoordinatesRequest(
                coordinates = listOf(userCoordinate, employeeCoordinate)
            )
        )
    }
}
