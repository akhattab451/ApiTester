package com.example.apitester.model

import java.io.Serializable

data class Request(
    val url: String,
    val requestType: Int,
    val headers: List<Pair<String, String>>,
    val queries: List<Pair<String, String>>,
    val requestBody: List<Pair<String, String>>?,
): Serializable

