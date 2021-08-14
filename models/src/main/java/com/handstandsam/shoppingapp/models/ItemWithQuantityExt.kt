package com.handstandsam.shoppingapp.models

fun List<ItemWithQuantity>.totalItemCount(): Int {
    var total = 0
    this.forEach {
        total += it.quantity.toInt()
    }
    return total
}