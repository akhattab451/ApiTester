package com.example.apitester

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val spinner: Spinner = findViewById(R.id.request_type_spinner)
        spinner.adapter = ArrayAdapter.createFromResource(
            this,
            R.array.spinner_items,
            android.R.layout.simple_spinner_dropdown_item,
        )

        val headers: RecyclerView = findViewById(R.id.headers_list)
        headers.adapter = ParamAdapter()
        val params: RecyclerView = findViewById(R.id.param_list)
        params.adapter = ParamAdapter()
        val body: RecyclerView = findViewById(R.id.body_list)
        body.adapter = ParamAdapter()


    }
}