package com.example.apitester.model

import java.io.Serializable

data class Request(
    val url: String,
    val requestType: Int,
    val headers: List<Pair<String, String>>?,
    val queries: List<Pair<String, String>>?,
    val requestBody: List<Pair<String, String>>?,
) : Serializable {
    fun getParams(): String {
        var paramsString = "{\n"

        if (headers != null) {
            paramsString += "Headers\n"
            headers.forEach {
                paramsString += "${it.first}: ${it.second}\n"
            }
        }
        if (queries != null) {
            paramsString += "Queries\n"
            queries.forEach {
                paramsString +="${it.first}: ${it.second}\n"
            }
        }
        if (requestBody != null) {
            paramsString += "Request Body\n"
            requestBody.forEach {
                paramsString += "${it.first}: ${it.second}\n"
            }
        }

        paramsString += "}\n"
        return paramsString
    }
}

