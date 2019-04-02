package com.handstandsam.shoppingapp.features.home

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

import com.handstandsam.shoppingapp.R
import com.handstandsam.shoppingapp.models.Category

import java.util.ArrayList

internal class HomeRVAdapter : RecyclerView.Adapter<CategoryViewHolder>() {
    private var categories: List<Category> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_recyclerview_category_row, parent, false)
        return CategoryViewHolder(view)
    }

    fun setData(categories: List<Category>) {
        this.categories = categories
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bindData(categories[position], position)
    }

    override fun getItemCount(): Int {
        return categories.size
    }
}
