package com.example.apitester

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.loader.app.LoaderManager
import androidx.loader.content.Loader
import com.example.apitester.async.RequestAsyncTaskLoader
import com.example.apitester.model.Request
import com.example.apitester.model.Response

class ResponseActivity : AppCompatActivity(), LoaderManager.LoaderCallbacks<Response> {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_response)

        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.title = "Response"
        }

        val request = intent.getSerializableExtra("request") as Request
        val bundle = Bundle().apply {
            putSerializable("request", request)
        }

        findViewById<TextView>(R.id.request_type_text).text = when(request.requestType) {
            0 -> "GET"
            else -> "POST"
        }

        findViewById<TextView>(R.id.url_text).text = request.url

        findViewById<TextView>(R.id.request_details_text).text = request.params()

        LoaderManager.getInstance(this).initLoader(1, bundle, this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        onBackPressed()
        return true
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Response> {
        return RequestAsyncTaskLoader(this, args)
    }

    override fun onLoadFinished(loader: Loader<Response>, data: Response?) {
        val progressBar: ProgressBar = findViewById(R.id.loading_indicator)
        progressBar.visibility = View.GONE

        when {
            data == null -> {
                findViewById<TextView>(R.id.error_text).apply {
                    text = "Error Retrieving Data"
                    visibility = View.VISIBLE
                }

            }
            data.error != null -> {
                findViewById<TextView>(R.id.error_text).apply {
                    text = data.error
                    visibility = View.VISIBLE
                }
            }
            else -> {
                findViewById<LinearLayout>(R.id.container).visibility = View.VISIBLE
                findViewById<TextView>(R.id.status_text_view).apply {
                    when(data.status) {
                        0 -> {
                            text = "SUCCESS"
                            setTextColor(Color.GREEN)
                        }
                        else -> {
                            text = "FAILURE"
                            setTextColor(Color.RED)
                        }
                    }
                }
                findViewById<TextView>(R.id.code_text_view).text = data.code.toString()
                findViewById<TextView>(R.id.response_text_view).text = data.responseBody
            }
        }
    }

    override fun onLoaderReset(loader: Loader<Response>) {}

}