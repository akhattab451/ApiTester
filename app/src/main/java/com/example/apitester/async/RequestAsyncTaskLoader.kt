package com.example.apitester.async

import android.content.ContentValues
import android.content.Context
import android.os.Bundle
import androidx.loader.content.AsyncTaskLoader
import com.example.apitester.Util
import com.example.apitester.data.ApiService
import com.example.apitester.data.database.RequestContract.RequestEntry
import com.example.apitester.data.database.RequestDBHelper
import com.example.apitester.model.Request
import com.example.apitester.model.Response
import java.lang.Exception

class RequestAsyncTaskLoader(context: Context, private val args: Bundle?)
    : AsyncTaskLoader<Response>(context) {

    private val db by lazy { RequestDBHelper(context).writableDatabase }

    override fun onStartLoading() {
        super.onStartLoading()
        forceLoad()
    }

    override fun loadInBackground(): Response {
        if (!Util.isConnected(context)) {
            return Response(error = "App is not connected to the internet")
        }

        val request = args!!.getSerializable("request") as Request

        val response: Response
        try {
           response = if (request.requestType == 0)
                ApiService.makeGetRequest(request)
            else
                ApiService.makePostRequest(request)
        } catch (e: Exception) {
            return Response(error = e.message)
        }

        val values = ContentValues().apply {
            put(RequestEntry.COLUMN_NAME_URL, request.url)
            put(RequestEntry.COLUMN_NAME_REQUEST_TYPE, request.requestType)
            put(RequestEntry.COLUMN_NAME_HEADERS, Util.pairListToString(RequestEntry.COLUMN_NAME_HEADERS, request.headers))
            put(RequestEntry.COLUMN_NAME_QUERIES, Util.pairListToString(RequestEntry.COLUMN_NAME_HEADERS, request.queries))
            put(RequestEntry.COLUMN_NAME_REQUEST_BODY, Util.pairListToString(RequestEntry.COLUMN_NAME_HEADERS, request.requestBody))
            put(RequestEntry.COLUMN_NAME_STATUS, response.status)
            put(RequestEntry.COLUMN_NAME_CODE, response.code)
            put(RequestEntry.COLUMN_NAME_RESPONSE_BODY, response.responseBody)
        }

        db.insert(RequestEntry.TABLE_NAME, null, values)
        return response
    }
}