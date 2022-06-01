package com.example.apitester

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import androidx.recyclerview.widget.RecyclerView
import com.example.apitester.model.Request
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.title = "Home"

        val spinner: Spinner = findViewById(R.id.request_type_spinner)
        spinner.adapter = ArrayAdapter.createFromResource(
            this,
            R.array.spinner_items,
            android.R.layout.simple_spinner_dropdown_item,
        )

        val headers: RecyclerView = findViewById(R.id.headers_list)
//        val headersDataSet = MutableList(1) { Pair("", "") }
        headers.adapter = ParamAdapter("Headers")

        val queries: RecyclerView = findViewById(R.id.query_list)
//        val queriesDataSet = MutableList(1) { Pair("", "") }
        queries.adapter = ParamAdapter("Query Parameters")

        val body: RecyclerView = findViewById(R.id.body_list)
//        val bodyDataSet = MutableList(1) { Pair("", "") }
        body.adapter = ParamAdapter("Request Body")

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                body.visibility = when (position) {
                    0 -> View.GONE
                    else -> View.VISIBLE
                }
            }

        }

        val url: EditText = findViewById(R.id.url_text)
        findViewById<FloatingActionButton>(R.id.send_fab)
            .setOnClickListener {
                if (url.text.isNullOrEmpty()) {
                    url.error = "Required field"
                    url.requestFocus()
                } else {
                    url.error = null
                    val request = Request(
                        url.text.toString(),
                        spinner.selectedItemPosition,
                        (headers.adapter as ParamAdapter).getParams(),
                        (queries.adapter as ParamAdapter).getParams(),
                        (body.adapter as ParamAdapter).getParams(),
                    )

                    Intent(this, ResponseActivity::class.java).apply {
                        putExtra("request", request)
                        startActivity(this)
                    }
                }
            }


    }
}