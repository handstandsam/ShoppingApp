package com.handstandsam.shoppingapp.models

fun List<ItemWithQuantity>.totalItemCount(): Long {
    var total = 0L
    this.forEach {
        total += it.quantity
    }
    return total
}