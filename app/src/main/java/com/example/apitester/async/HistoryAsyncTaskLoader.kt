package com.example.apitester.async

import android.content.Context
import android.os.Bundle
import androidx.loader.content.AsyncTaskLoader
import com.example.apitester.model.Request

class HistoryAsyncTaskLoader(context: Context, arg: Bundle?)
    : AsyncTaskLoader<List<Request>>(context) {
}