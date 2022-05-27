package com.example.chattest.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<T>(@LayoutRes val layoutID: Int) : RecyclerView.Adapter<ViewHolder>() {

    private var items = arrayListOf<T>()

    open fun setData(data: ArrayList<T>) {
        items = data
        notifyDataSetChanged()
    }

    open fun getData() = items

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(parent.inflate(layoutID))

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.apply {
            bindViewHolder(this, items[holder.adapterPosition])
        }
    }

    abstract fun bindViewHolder(holder: ViewHolder, data: T)

}

class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

internal fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
}