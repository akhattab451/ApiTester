package com.example.apitester.async

import android.content.Context
import android.os.Bundle
import androidx.loader.content.AsyncTaskLoader
import com.example.apitester.data.ApiService
import com.example.apitester.model.Request
import com.example.apitester.model.Response

class RequestAsyncTaskLoader(context: Context, private val args: Bundle?)
    : AsyncTaskLoader<Response>(context) {
    override fun onStartLoading() {
        super.onStartLoading()
        forceLoad()
    }

    override fun loadInBackground(): Response {
        val request = args!!.getSerializable("request") as Request

        print("XXXX  $request")

        return if (request.requestType == 0)
            ApiService.makeGetRequest(request)
        else
            ApiService.makePostRequest(request)

    }
}