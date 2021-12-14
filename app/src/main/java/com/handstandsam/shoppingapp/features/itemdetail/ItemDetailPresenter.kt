package com.handstandsam.shoppingapp.features.itemdetail

import android.content.Intent
import com.handstandsam.shoppingapp.cart.ShoppingCart
import com.handstandsam.shoppingapp.models.Item
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.Serializable

class ItemDetailPresenter(
    private val view: ItemDetailActivity.ItemDetailView,
    private var cart: ShoppingCart
) {
    internal lateinit var item: Item

    fun onResume(intent: Intent) {
        val extras = intent.extras
        val item = extras!!.get(BUNDLE_PARAM_ITEM) as ItemDetailData
        this.item = item.toItem()

        view.setLabel(item.label)
        view.setImageUrl(item.image)
        view.setActionBarTitle(item.label)
    }

    fun addToCardClicked() {
        CoroutineScope(Dispatchers.IO).launch {
            cart.incrementItemInCart(item)
        }
    }

    companion object {
        const val BUNDLE_PARAM_ITEM = "item"
    }
}
