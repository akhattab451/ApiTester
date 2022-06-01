package com.example.apitester.data.database

import android.provider.BaseColumns

object RequestContract {


    object RequestEntry : BaseColumns {
        const val TABLE_NAME = "request"
        const val COLUMN_NAME_URL = "url"
        const val COLUMN_NAME_REQUEST_TYPE = "request_type"
        const val COLUMN_NAME_HEADERS = "headers"
        const val COLUMN_NAME_QUERIES = "queries"
        const val COLUMN_NAME_REQUEST_BODY = "request_body"
        const val COLUMN_NAME_STATUS = "status"
        const val COLUMN_NAME_CODE = "code"
        const val COLUMN_NAME_RESPONSE_BODY = "response_body"
        const val COLUMN_NAME_TIMESTAMP = "timestamp"
    }
}