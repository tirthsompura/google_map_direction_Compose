package com.example.googlemapdirection.nointernet

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkInfo
import android.net.NetworkRequest
import android.os.Build
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.example.googlemapdirection.nointernet.utils.NoInternetUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class NoInternetObserveComponent(
    private val applicationContext: Context,
    lifecycle: Lifecycle,
    private val listener: NoInternetObserverListener
) : LifecycleObserver {

    companion object {
        private const val TAG = "NoInternetObserveComponent"
    }

    private val coroutineScope = CoroutineScope(Dispatchers.IO)
    private var currentJob: Job? = null

    private var connectivityManager: ConnectivityManager =
        applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    private var connectivityManagerCallback: ConnectivityManager.NetworkCallback? = null

    init {

        // Add to the lifecycle owner observer list.
        lifecycle.addObserver(this)
    }

    private fun initReceivers() {

        updateConnection()

        when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.N -> {
                connectivityManager.registerDefaultNetworkCallback(
                    getConnectivityManagerCallback()
                )
            }

            Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP -> {
                val builder = NetworkRequest.Builder()
                    .addTransportType(android.net.NetworkCapabilities.TRANSPORT_CELLULAR)
                    .addTransportType(android.net.NetworkCapabilities.TRANSPORT_WIFI)
                    .addTransportType(android.net.NetworkCapabilities.TRANSPORT_ETHERNET)
                connectivityManager.registerNetworkCallback(
                    builder.build(),
                    getConnectivityManagerCallback()
                )
            }
        }

    }

    private fun updateConnection() {

        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
        if (activeNetwork?.isConnected == true) {

            listener.onConnected()
        } else {

            checkInternetAndInvokeListener()
        }
    }

    private fun getConnectivityManagerCallback(): ConnectivityManager.NetworkCallback {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            connectivityManagerCallback = object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {

                    listener.onConnected()
                }

                override fun onLost(network: Network) {

                    checkInternetAndInvokeListener()
                }
            }

            return connectivityManagerCallback!!
        } else {
            throw IllegalAccessError("This should not happened")
        }
    }

    /**
     * Check if the device is connected to the internet.
     * It checks if the device is connected (by wifi or data connection etc.) to the internet,
     * and ping google to make sure the connection is active.
     */
    private fun checkInternetAndInvokeListener() {

        currentJob = coroutineScope.launch {
            val hasActiveConnection = NoInternetUtils.isConnectedToInternet(applicationContext)
                    && NoInternetUtils.hasActiveInternetConnection()

            if (!hasActiveConnection) {
                launch(Dispatchers.Main) {
                    listener.onDisconnected(NoInternetUtils.isAirplaneModeOn(applicationContext))
                }
            }
        }
    }

    /**
     * Start listening.
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun start() {

        listener.onStart()

        initReceivers()
    }

    /**
     * Stop listening.
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun stop() {

        connectivityManagerCallback?.let {
            try {
                connectivityManager.unregisterNetworkCallback(it)
            } catch (e: Exception) {
                /* no-op */
            }
        }

        currentJob?.cancel()

        listener.onStop()
    }


    interface NoInternetObserverListener {
        fun onStart()
        fun onConnected()
        fun onDisconnected(isAirplaneModeOn: Boolean)
        fun onStop()
    }
}