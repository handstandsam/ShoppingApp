package com.handstandsam.shoppingapp.features.itemdetail

import android.content.Intent
import com.handstandsam.shoppingapp.models.Item
import com.handstandsam.shoppingapp.repository.CheckoutCart

class ItemDetailPresenter(
    private val view: ItemDetailActivity.ItemDetailView,
    private var cart: CheckoutCart
) {
    internal lateinit var item: Item

    fun onResume(intent: Intent) {

        val extras = intent.extras
        val item = extras!!.get(BUNDLE_PARAM_ITEM) as Item
        this.item = item

        view.setLabel(item.label)
        view.setImageUrl(item.image)
        view.setActionBarTitle(item.label)
    }

    fun addToCardClicked() {
        cart.addItem(item)
    }

    companion object {
        const val BUNDLE_PARAM_ITEM = "item"
    }
}
