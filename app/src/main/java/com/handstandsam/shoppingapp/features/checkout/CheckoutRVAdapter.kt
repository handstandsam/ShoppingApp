package com.handstandsam.shoppingapp.features.checkout

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.handstandsam.shoppingapp.R
import com.handstandsam.shoppingapp.models.Item

internal class CheckoutRVAdapter : RecyclerView.Adapter<CheckoutItemRowViewHolder>() {

    val items: MutableList<Item> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CheckoutItemRowViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_recyclerview_cart_item_row, parent, false)
        return CheckoutItemRowViewHolder(view)
    }

    override fun onBindViewHolder(holder: CheckoutItemRowViewHolder, position: Int) {
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
