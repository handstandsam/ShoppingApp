package com.handstandsam.shoppingapp.features.category

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.handstandsam.shoppingapp.R
import com.handstandsam.shoppingapp.models.Item

internal class CategoryRVAdapter : RecyclerView.Adapter<ItemRowViewHolder>() {

    val items: MutableList<Item> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemRowViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_recyclerview_item_row, parent, false)
        return ItemRowViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemRowViewHolder, position: Int) {
        holder.bindData(items[position], position)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setItems(items: List<Item>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }
}
