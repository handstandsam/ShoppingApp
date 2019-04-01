package com.handstandsam.shoppingapp.features.checkout

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
import com.handstandsam.shoppingapp.repository.ItemWithQuantity


internal class CheckoutItemRowViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val textView: TextView = itemView.findViewById(R.id.text)

    private val imageView: AppCompatImageView = itemView.findViewById(R.id.image)

    private var _itemWithQuantity: ItemWithQuantity? = null

    init {
        itemView.setOnClickListener { view ->
            val context = itemView.context
            val intent = Intent(view.context, ItemDetailActivity::class.java)
            val extras = Bundle()
            extras.putSerializable(ItemDetailPresenter.BUNDLE_PARAM_ITEM, _itemWithQuantity?.item)
            intent.putExtras(extras)
            context.startActivity(intent)
        }
    }

    fun bindData(itemWithQuantity: ItemWithQuantity, position: Int) {
        this._itemWithQuantity = itemWithQuantity

        val imageUrl = itemWithQuantity.item.image
        if (!imageUrl.isEmpty()) {
            Glide.with(imageView.context).load(imageUrl).into(imageView)
        } else {
            itemView.setBackgroundResource(ColorInts.getColor(position))
        }

        textView.text = itemWithQuantity.quantity.toString() + " x " + itemWithQuantity.item.label
    }
}
