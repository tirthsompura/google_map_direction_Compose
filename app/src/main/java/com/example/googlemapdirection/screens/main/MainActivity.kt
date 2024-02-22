package com.example.googlemapdirection.screens.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.googlemapdirection.navigations.RootNavGraph
import com.example.googlemapdirection.nointernet.NoInternetObserveComponent
import com.example.googlemapdirection.nointernet.NoInternetUIDialog
import com.example.googlemapdirection.ui.theme.GoogleMapDirectionTheme
import com.example.googlemapdirection.utils.SessionManagerClass
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private var sessionManagerClass: SessionManagerClass? = null
    private var noInternetObserveComponent: NoInternetObserveComponent? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sessionManagerClass = SessionManagerClass(applicationContext)
        noInternetObserveComponent = NoInternetObserveComponent(this, lifecycle,
            object : NoInternetObserveComponent.NoInternetObserverListener {
                override fun onStart() {
                    /* no-op */
                }

                override fun onConnected() {
                    sessionManagerClass?.showNoInternetDialog?.value = false
                    if (sessionManagerClass != null) {
                        sessionManagerClass?.showNoInternetDialog?.value = false
                    }
                }

                override fun onDisconnected(isAirplaneModeOn: Boolean) {
                    if (sessionManagerClass != null) {
                        sessionManagerClass?.showNoInternetDialog?.value = true
                    }
                }

                override fun onStop() {
                    if (sessionManagerClass != null) {
                        sessionManagerClass?.showNoInternetDialog?.value = false
                    }
                }
            }
        )

        setContent {
            GoogleMapDirectionTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    if (sessionManagerClass?.showNoInternetDialog?.value == true) {
                        NoInternetUIDialog()
                    }
                    RootNavGraph(sessionManagerClass!!)
                }
            }
        }
    }
}