package com.handstandsam.shoppingapp.features.checkout

import com.handstandsam.shoppingapp.models.Item

object CartContentsMessage {

    fun itemsText(items: List<Item>): String {
        val itemsMap = mutableMapOf<String, MutableList<Item>>()
        items.forEach { item ->
            val key = item.label
            var list: MutableList<Item>? = itemsMap[key]
            if (list == null) {
                list = mutableListOf()
            }
            list.add(item)
            itemsMap[key] = list
        }
        val sb = StringBuilder()
        for ((key, value) in itemsMap) {
            sb.append("* " + value.size + " " + key + "\n")
        }

        return sb.toString()
    }

    fun itemCountText(items: List<Item>): String {
        return items.size.toString() + " item(s) in your cart."
    }
}
