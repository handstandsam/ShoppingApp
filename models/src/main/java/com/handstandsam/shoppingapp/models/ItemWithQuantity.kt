package com.handstandsam.shoppingapp.models

data class ItemWithQuantity(val item: Item, val quantity: Int)

fun List<ItemWithQuantity>.totalItemCount(): Int {
    var total = 0
    this.forEach {
        total += it.quantity
    }
    return total
}