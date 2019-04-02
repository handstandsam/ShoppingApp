package com.handstandsam.shoppingapp.features.home

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.handstandsam.shoppingapp.R
import com.handstandsam.shoppingapp.features.category.CategoryActivity
import com.handstandsam.shoppingapp.features.category.CategoryActivity.Companion.BUNDLE_PARAM_CATEGORY
import com.handstandsam.shoppingapp.models.Category


internal class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val textView: TextView = itemView.findViewById(R.id.text)

    private val imageView: ImageView = itemView.findViewById(R.id.image)

    lateinit var category: Category

    init {
        itemView.setOnClickListener {
            val context = itemView.context
            val intent = Intent(context, CategoryActivity::class.java)
            val extras = Bundle()
            extras.putSerializable(BUNDLE_PARAM_CATEGORY, category)
            intent.putExtras(extras)
            context.startActivity(intent)
        }
    }

    fun bindData(category: Category, position: Int) {
        this.category = category

        val imageUrl = category.image
        if (!imageUrl.isEmpty()) {
            Glide.with(imageView.context).load(category.image).into(imageView)
        } else {
            itemView.setBackgroundResource(ColorInts.getColor(position))
        }

        textView.text = this.category.label
    }
}