package com.handstandsam.shoppingapp.features.category

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.AppCompatImageView
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.bumptech.glide.Glide
import com.handstandsam.shoppingapp.R
import com.handstandsam.shoppingapp.features.home.ColorInts
import com.handstandsam.shoppingapp.features.itemdetail.ItemDetailActivity
import com.handstandsam.shoppingapp.features.itemdetail.ItemDetailPresenter
import com.handstandsam.shoppingapp.models.Item


internal class ItemRowViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val textView: TextView = itemView.findViewById(R.id.text)

    val imageView: AppCompatImageView = itemView.findViewById(R.id.image)

    private var item: Item? = null

    init {
        itemView.setOnClickListener { view ->
            val context = itemView.context
            val intent = Intent(view.context, ItemDetailActivity::class.java)
            val extras = Bundle()
            extras.putSerializable(ItemDetailPresenter.BUNDLE_PARAM_ITEM, item)
            intent.putExtras(extras)
            context.startActivity(intent)
        }
    }

    fun bindData(item: Item, position: Int) {
        this.item = item

        val imageUrl = item.image
        if (imageUrl != null && !imageUrl.isEmpty()) {
            Glide.with(imageView.context).load(item.image).into(imageView!!)
        } else {
            itemView.setBackgroundResource(ColorInts.getColor(position))
        }

        textView.text = this.item!!.label
    }
}
