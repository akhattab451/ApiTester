package com.example.apitester

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView

class ParamAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val params = MutableList(1) { Pair("", "") }

    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view)

    class FooterViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == 0) ItemViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.list_item_param,
                parent,
                false
            )
        ) else FooterViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.footer_param_list,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is FooterViewHolder) {
            (holder.itemView as Button).setOnClickListener {
                params.add(Pair("", ""))
                notifyItemChanged(position)
            }
        }
    }

    override fun getItemCount(): Int = params.size + 1

    override fun getItemViewType(position: Int): Int = if (position == itemCount - 1) 1 else 0

    override fun getItemId(position: Int): Long = position.toLong()
}