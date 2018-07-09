package com.handstandsam.shoppingapp.features.checkout

import com.handstandsam.shoppingapp.models.Item
import com.handstandsam.shoppingapp.repository.CheckoutCart
import java.util.*

class CheckoutPresenter(
    private val view: CheckoutActivity.CheckoutView,
    private var cart: CheckoutCart
) {

    fun onResume() {
        val items = cart.items

        val itemsCountText = items.size.toString() + " item(s) in your cart."

        view.setItemCountText(itemsCountText)

        val itemsMap = mutableMapOf<String, MutableList<Item>>()
        for (item in items) {
            val key = item.label
            var list: MutableList<Item>? = itemsMap[key]
            if (list == null) {
                list = ArrayList<Item>()
            }
            list.add(item)
            if (key != null) {
                itemsMap.put(key, list)
            }
        }
        val sb = StringBuilder()
        for ((key, value) in itemsMap) {
            sb.append("* " + value.size + " " + key + "\n")
        }

        view.setItemsText(sb.toString())
    }

}
