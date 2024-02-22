package com.example.googlemapdirection.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import androidx.core.app.ActivityCompat
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory


fun checkForPermission(context: Context): Boolean {
    return !(ActivityCompat.checkSelfPermission(
        context,
        Manifest.permission.ACCESS_FINE_LOCATION
    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
        context,
        Manifest.permission.ACCESS_COARSE_LOCATION
    ) != PackageManager.PERMISSION_GRANTED)
}

fun String.capitaliseIt() = this.lowercase().capitalize(Locale.current)


fun bitmapDescriptor(
    resId: Bitmap,
): BitmapDescriptor {

//    val drawable = ContextCompat.getDrawable(context, resId) ?: return null
//    drawable.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
//    val bm = Bitmap.createBitmap(
//        drawable.intrinsicWidth,
//        drawable.intrinsicHeight,
//        Bitmap.Config.ARGB_8888
//    )

//    val canvas = android.graphics.Canvas(bm)
//    drawable.draw(canvas)
    return BitmapDescriptorFactory.fromBitmap(resId)
}