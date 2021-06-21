package com.abramchuk.itbookstore.manager

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

class NetworkManager(private var applicationContext: Context) {

    val isConnectedToInternet: Boolean
        get() {
            val connectivityManager = applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
            return activeNetwork?.isConnectedOrConnecting == true
        }
}