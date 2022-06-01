package com.example.apitester.async

import android.content.Context
import android.os.Bundle
import androidx.loader.content.AsyncTaskLoader
import com.example.apitester.Util
import com.example.apitester.data.database.RequestContract.RequestEntry
import com.example.apitester.data.database.RequestDBHelper
import com.example.apitester.model.Request


class HistoryAsyncTaskLoader(context: Context, private val database: RequestDBHelper, private val args: Bundle?) :
    AsyncTaskLoader<List<Request>>(context) {

    override fun onStartLoading() {
        super.onStartLoading()
        forceLoad()
    }

    override fun loadInBackground(): List<Request> {
        val requests = mutableListOf<Request>()
        val sortOrder = "${RequestEntry.COLUMN_NAME_TIMESTAMP} DESC"

        val db = database.readableDatabase

        val cursor = db.query(
            RequestEntry.TABLE_NAME,
            null,
            null,
            null,
            null,
            null,
            sortOrder
        )

        with(cursor) {
            while (moveToNext()) {
                requests.add(
                    Request(
                        url = getString(getColumnIndexOrThrow(RequestEntry.COLUMN_NAME_URL)),
                        requestType = getInt(getColumnIndexOrThrow(RequestEntry.COLUMN_NAME_REQUEST_TYPE)),
                        headersString = getString(getColumnIndexOrThrow(RequestEntry.COLUMN_NAME_HEADERS)),
                        queriesString = getString(getColumnIndexOrThrow(RequestEntry.COLUMN_NAME_QUERIES)),
                        requestBodyString = getString(getColumnIndexOrThrow(RequestEntry.COLUMN_NAME_REQUEST_BODY)),
                        status = getInt(getColumnIndexOrThrow(RequestEntry.COLUMN_NAME_STATUS)),
                        code = getInt(getColumnIndexOrThrow(RequestEntry.COLUMN_NAME_CODE)),
                        responseBody = getString(getColumnIndexOrThrow(RequestEntry.COLUMN_NAME_RESPONSE_BODY)),
                        timestamp = getLong(getColumnIndexOrThrow(RequestEntry.COLUMN_NAME_TIMESTAMP))
                    )
                )
            }
        }

        return requests
    }

    override fun onStopLoading() {
        super.onStopLoading()
        database.close()
    }
}