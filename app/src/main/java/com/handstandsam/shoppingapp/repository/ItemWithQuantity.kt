package com.handstandsam.shoppingapp.repository

import com.handstandsam.shoppingapp.models.Item

data class ItemWithQuantity(val item: Item, val quantity: Int)

fun List<ItemWithQuantity>.totalItemCount(): Int {
    var total = 0
    this.forEach {
        total += it.quantity
    }
    return total
}