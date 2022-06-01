package com.example.apitester.data

import com.example.apitester.model.Request
import com.example.apitester.model.Response
import java.io.BufferedWriter
import java.net.HttpURLConnection
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class ApiService private constructor() {

    companion object {
        fun makeGetRequest(request: Request): Response {
            val urlString = request.url
            if (request.queries.isNotEmpty()) {
                urlString.plus("?" + request.queries.forEach { it.first + "=" + it.second })
            }

            val url = URL(urlString)

            val connection = if (url.protocol.lowercase() == "http")  url.openConnection() as HttpURLConnection else url.openConnection() as HttpsURLConnection
            connection.apply {
                requestMethod = "GET"
                doInput = true
                doOutput = false
                request.headers.forEach {
                    if (it.first.isNotEmpty())
                        setRequestProperty(it.first, it.second)
                }


                val responseCode = connection.responseCode
                val data: String
                val status: Int
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    data = connection.inputStream.bufferedReader().readText()
                    status = 0
                } else {
                    data = connection.errorStream.bufferedReader().readText()
                    status = 1
                }

                return Response(status, responseCode, data)

            }
        }

        fun makePostRequest(request: Request): Response {
            val urlString = request.url
            if (request.queries.isNotEmpty()) {
                urlString.plus("?" + request.queries.forEach { it.first + "=" + it.second })
            }

            val url = URL(urlString)

            val connection = url.openConnection() as HttpURLConnection
            connection.apply {
                requestMethod = "POST"
                doInput = true
                doOutput = true
                request.headers.forEach {
                    if (it.first.isNotEmpty()) {
                        setRequestProperty(it.first, it.second)
                    }
                }

                val requestBodyWriter: BufferedWriter = connection.outputStream.bufferedWriter()

                request.requestBody?.forEach {
                    if (it.first.isNotEmpty()) {
                        requestBodyWriter.write("${it.first} : ${it.second}")
                    }
                }

                val responseCode = connection.responseCode
                val data: String
                val status: Int
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    data = connection.inputStream.bufferedReader().readText()
                    status = 0
                } else {
                    data = connection.errorStream.bufferedReader().readText()
                    status = 1
                }

                return Response(status, responseCode, data)
            }
        }
    }
}