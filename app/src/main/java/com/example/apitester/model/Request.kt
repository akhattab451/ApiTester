package com.example.apitester.model

import java.io.Serializable
import java.sql.Timestamp

data class Request(
    val url: String,
    val requestType: Int,
    val headers: List<Pair<String, String>>? = null,
    val headersString: String? = null,
    val queries: List<Pair<String, String>>? = null,
    val queriesString: String? = null,
    val requestBody: List<Pair<String, String>>? = null,
    val requestBodyString: String? = null,
    val status: Int? = null,
    val code: Int? = null,
    val responseBody: String? = null,
    val error: String? = null,
    val timestamp: Long? = null
) : Serializable

