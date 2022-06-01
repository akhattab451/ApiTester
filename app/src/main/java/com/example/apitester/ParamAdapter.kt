package com.example.apitester

import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.RecyclerView

class ParamAdapter(
    private val title: String,
    private val params: MutableList<Pair<String, String>>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val keyEditText: EditText = view.findViewById(R.id.key_edit_text)
        val valueEditText: EditText = view.findViewById(R.id.value_edit_text)
        var keyWatcher: TextWatcher? = null
        var valueWatcher: TextWatcher? = null

    }

    class FooterViewHolder(view: View) : RecyclerView.ViewHolder(view)

    class HeaderViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            0 -> HeaderViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.header_param_list,
                    parent,
                    false
                )
            )
            itemCount - 1 -> FooterViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.footer_param_list,
                    parent,
                    false
                )
            )
            else -> ItemViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.list_item_param,
                    parent,
                    false
                )
            )


        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is FooterViewHolder -> {
                (holder.itemView as Button).setOnClickListener {
                    params.add(Pair("", ""))
                    notifyItemChanged(position)
                }
            }
            is HeaderViewHolder -> {
                (holder.itemView as TextView).text = title
            }
            is ItemViewHolder -> {
                holder.keyEditText.doAfterTextChanged { text ->
                    if (!text.isNullOrBlank()) {
                        params[position - 1].copy(first = text.toString())
                    }

                }

                holder.valueEditText.doAfterTextChanged { text ->
                    if (!text.isNullOrBlank()) {
                        params[position - 1].copy(second = text.toString())
                    }
                }
            }
        }
    }

    override fun getItemCount(): Int = params.size + 2

    override fun getItemViewType(position: Int): Int = position

    override fun getItemId(position: Int): Long = position.toLong()
}