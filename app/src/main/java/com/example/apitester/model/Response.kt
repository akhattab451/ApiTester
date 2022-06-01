package com.example.apitester.model

data class Response(
    val status: Int? = null,
    val code: Int? = null,
    val responseBody: String? = null,
    val error: String? = null
)
