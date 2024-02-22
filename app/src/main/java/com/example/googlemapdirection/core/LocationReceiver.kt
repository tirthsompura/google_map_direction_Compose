package com.example.firebasechatdemo.core

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.HandlerThread
import android.os.Process
import android.util.Log
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationAvailability
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.model.LatLng

class LocationReceiver(val context: Context, getLetLong: (LatLng) -> Unit) {

    private var mFusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)
    private var lastLocation: Location? = null

    private val mLocationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 1000)
        .setWaitForAccurateLocation(false)
        .setMinUpdateIntervalMillis(1000)
        .setMaxUpdateDelayMillis(1000)
        .build()

    val mLocationCallback = object : LocationCallback() {

        override fun onLocationResult(locationResult: LocationResult) {
            Log.e("LocationReceiver", " location ${locationResult.locations}")
            if (locationResult.lastLocation == null) {
                Log.e("LocationReceiver", "Location not found!")
                return
            }
            lastLocation = locationResult.lastLocation
            getLetLong(LatLng(lastLocation?.latitude ?: 0.0, lastLocation?.longitude ?: 0.0))
            lastLocation?.longitude
            lastLocation?.latitude
            Log.d(
                "##LET_LONG_RECEIVER",
                "LET::${lastLocation?.latitude}, LONG::${lastLocation?.longitude}"
            )

//            getLetLong(locationResult.locations)
            /* Log.e(
                 "MyLocationUpdateManager",
                 "onLocationResult: Location Updated ${lastLocation?.latitude},${lastLocation?.longitude}",
             )*/
        }

        override fun onLocationAvailability(locationAvailability: LocationAvailability) {
            super.onLocationAvailability(locationAvailability)
            /* Log.e(
                 "MyLocationUpdateManager",
                 "onLocationAvailability: ${locationAvailability?.isLocationAvailable}"
             )*/
        }

    }

    /**
     * This method will add location callback safely
     */
    @SuppressLint("MissingPermission")
    fun startLocationUpdates() {
        if (isPermissionGranted(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) && isPermissionGranted(context, Manifest.permission.ACCESS_COARSE_LOCATION)
        ) {
            mFusedLocationClient.lastLocation.addOnSuccessListener {

            }
            mFusedLocationClient.removeLocationUpdates(mLocationCallback).addOnCompleteListener {
                val handlerThread =
                    HandlerThread("locationUpdate", Process.THREAD_PRIORITY_BACKGROUND)
                mFusedLocationClient.requestLocationUpdates(
                    mLocationRequest,
                    mLocationCallback,
                    handlerThread.looper
                )
            }
        }
    }

    /**
     * Remove location callback
     */
    fun stopLocationUpdates() {
        mFusedLocationClient.removeLocationUpdates(mLocationCallback)
    }

    fun getLocation() = lastLocation

    fun isPermissionGranted(context: Context, permission: String): Boolean =
        ActivityCompat.checkSelfPermission(
            context, permission
        ) == PackageManager.PERMISSION_GRANTED
}