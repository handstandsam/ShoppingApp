package com.handstandsam.shoppingapp.features.itemdetail

import android.content.Intent
import com.handstandsam.shoppingapp.models.Item
import com.handstandsam.shoppingapp.repository.CheckoutCart
import com.handstandsam.shoppingapp.repository.SessionManager
import javax.inject.Inject

class ItemDetailPresenter(private val view: ItemDetailActivity.ItemDetailView) {

    @Inject
    lateinit internal var sessionManager: SessionManager

    @Inject
    lateinit internal var cart: CheckoutCart

    lateinit internal var item: Item

    init {
        view.appComponent.inject(this)
    }

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
        view.showToast(item.label + " added to cart.")
    }

    companion object {

        val BUNDLE_PARAM_ITEM = "item"
    }
}
