package com.handstandsam.shoppingapp.models

data class ItemWithQuantity(val item: Item, val quantity: Long)

fun List<ItemWithQuantity>.totalItemCount(): Long {
    var total = 0L
    this.forEach {
        total += it.quantity
    }
    return total
}