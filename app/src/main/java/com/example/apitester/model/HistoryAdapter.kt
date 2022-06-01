package com.example.apitester


import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.RecyclerView
import com.example.apitester.model.Request
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class HistoryAdapter(
    private val requests: List<Request>
) : RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val requestTypeText: TextView = view.findViewById(R.id.request_type_text)
        val urlText: TextView = view.findViewById(R.id.url_text)
        val requestDetailsText: TextView = view.findViewById(R.id.request_details_text)
        val statusText: TextView = view.findViewById(R.id.status_text_view)
        val codeText: TextView = view.findViewById(R.id.code_text_view)
        val responseText: TextView = view.findViewById(R.id.response_text_view)
//        val timestampText: TextView = view.findViewById(R.id.timestamp)
//        val divider: View = view.findViewById(R.id.divider)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.list_item_request,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val request = requests[position]

        holder.apply {
            requestTypeText.text = if (request.requestType == 0) "GET" else "POST"
            urlText.text = request.url
            requestDetailsText.apply {
                visibility = View.VISIBLE
                text = request.headersString + request.queriesString + request.requestBodyString
            }
            statusText.text = if (request.status == 0) "SUCCESS" else "FAILURE"
            codeText.text = request.code.toString()
            responseText.text = request.responseBody
//            timestampText.apply {
//                visibility = View.VISIBLE
//                text = SimpleDateFormat("dd/mm/yyyy").format(Date(request.timestamp!!.times(1000)))
//            }
//            divider.visibility = View.VISIBLE

        }

    }

    override fun getItemCount(): Int = requests.size
}




