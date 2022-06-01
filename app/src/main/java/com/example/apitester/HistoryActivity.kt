package com.example.apitester

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.loader.app.LoaderManager
import androidx.loader.content.Loader
import androidx.recyclerview.widget.RecyclerView
import com.example.apitester.async.HistoryAsyncTaskLoader
import com.example.apitester.data.database.RequestDBHelper
import com.example.apitester.model.Request
import com.example.apitester.model.Response

class HistoryActivity : AppCompatActivity(), LoaderManager.LoaderCallbacks<List<Request>> {

    lateinit var database: RequestDBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        database = RequestDBHelper(this)

        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.title = "History"
        }

        LoaderManager.getInstance(this).initLoader(2, null, this)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        onBackPressed()
        return true
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<List<Request>> {
        return HistoryAsyncTaskLoader(this, database, null)
    }

    override fun onLoadFinished(loader: Loader<List<Request>>, data: List<Request>?) {
        val progressBar: ProgressBar = findViewById(R.id.loading_indicator)
        progressBar.visibility = View.GONE

        if (data == null) {
            findViewById<TextView>(R.id.error_text).apply {
                text = "Error Retrieving Data"
                visibility = View.VISIBLE
            }
        } else {
            val requests: RecyclerView = findViewById(R.id.request_history_list)
            requests.adapter = HistoryAdapter(data)
        }
    }

    override fun onLoaderReset(loader: Loader<List<Request>>) {}
}