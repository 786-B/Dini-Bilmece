package com.ykp.DiniBilmece.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities


fun checkNetworkState(context: Context): Boolean {

    //instance of connectivityManager
    val connectivityManager = context.getSystemService(ConnectivityManager::class.java)

    //get a reference to the current default network
    val currentNetwork = connectivityManager.activeNetwork


    val caps = connectivityManager.getNetworkCapabilities(currentNetwork) ?: return false
    val linkProperties = connectivityManager.getLinkProperties(currentNetwork) ?: return false

    return when {
        caps.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
        else -> {
            return false
        }
    }
}