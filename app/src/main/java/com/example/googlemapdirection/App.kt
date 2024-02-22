package com.example.googlemapdirection

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import androidx.navigation.NavHostController
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {
    companion object {
        @SuppressLint("StaticFieldLeak")
        var context: Context? = null

        @SuppressLint("StaticFieldLeak")
        var navHostController: NavHostController? = null
    }

    override fun onCreate() {
        super.onCreate()
        context = this
    }
}