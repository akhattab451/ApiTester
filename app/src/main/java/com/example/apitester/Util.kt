package com.example.apitester

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build

object Util {

    fun isConnected(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
                ?.let { networkCapabilities ->
                    return networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                            || networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                }
        } else {
            return connectivityManager.activeNetworkInfo?.isConnectedOrConnecting == true
        }

        return false
    }

    fun pairListToString(list: List<Pair<String, String>>?): String {
        if (list == null)
            return "[]"

        var listString = "[\n"
        list.forEach {
            if (it.first.isNotEmpty()) {
                listString += "\t${it.first}: ${it.second},\n"
            }
        }
        listString += "]\n"

        return listString
    }

    fun getValidParamList(params: List<Pair<String, String>>): List<Pair<String, String>>? {
        if (params.size == 1 && params[0].first.isEmpty())
            return null
        return params
    }

    fun listFromString(listString: String): List<Pair<String, String>> {
        return listString.takeIf { it != "[" || it != "]" || it != "\n" || it != "\t" }!!.split(",").map {
            val pair = it.split(":")
             Pair(pair[0], pair[1])
        }
    }

}