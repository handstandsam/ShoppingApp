package com.handstandsam.shoppingapp.features.home

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import butterknife.ButterKnife
import com.bumptech.glide.Glide
import com.handstandsam.shoppingapp.R
import com.handstandsam.shoppingapp.features.category.CategoryActivity
import com.handstandsam.shoppingapp.features.category.CategoryPresenter
import com.handstandsam.shoppingapp.models.Category


internal class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val textView: TextView = itemView.findViewById(R.id.text)

    val imageView: ImageView = itemView.findViewById(R.id.image)

    var category: Category? = null

    init {
        ButterKnife.bind(this, itemView)
        itemView.setOnClickListener {
            val context = itemView.context
            val intent = Intent(context, CategoryActivity::class.java)
            val extras = Bundle()
            extras.putSerializable(CategoryPresenter.BUNDLE_PARAM_CATEGORY, category)
            intent.putExtras(extras)
            context.startActivity(intent)
        }
    }

    fun bindData(category: Category, position: Int) {
        this.category = category

        val imageUrl = category.image
        if (imageUrl != null && !imageUrl.isEmpty()) {
            Glide.with(imageView.context).load(category.image).into(imageView!!)
        } else {
            itemView.setBackgroundResource(ColorInts.getColor(position))
        }

        textView.text = this.category!!.label
    }
}
