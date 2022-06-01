package com.example.apitester

import android.content.Intent
import android.content.res.TypedArray
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import androidx.recyclerview.widget.RecyclerView
import com.example.apitester.model.Request
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private val headersDataSet = MutableList(1) { Pair("", "") }
    private val queriesDataSet = MutableList(1) { Pair("", "") }
    private val bodyDataSet = MutableList(1) { Pair("", "") }

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

        findViewById<RecyclerView>(R.id.headers_list).adapter =
            ParamAdapter("Headers", headersDataSet)

        findViewById<RecyclerView>(R.id.query_list).adapter =
            ParamAdapter("Query Parameters", queriesDataSet)

        val body = findViewById<RecyclerView>(R.id.body_list)
        body.adapter = ParamAdapter("Request Body", bodyDataSet)

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                when (position) {
                    0 -> {
                        body.visibility = View.GONE
                    }
                    else -> {
                        body.visibility = View.VISIBLE
                    }
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
                        headers = Util.getValidParamList(headersDataSet),
                        queries = Util.getValidParamList(queriesDataSet),
                        requestBody = Util.getValidParamList(bodyDataSet)
                    )

                    Intent(this, ResponseActivity::class.java).apply {
                        putExtra("request", request)
                        startActivity(this)
                    }
                }
            }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Intent(this, HistoryActivity::class.java).apply {
            startActivity(this)
        }
        return true
    }

}