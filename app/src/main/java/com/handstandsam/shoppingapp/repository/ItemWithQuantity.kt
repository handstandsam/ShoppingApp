package com.handstandsam.shoppingapp.repository

import com.handstandsam.shoppingapp.models.Item

data class ItemWithQuantity(val item: Item, val quantity: Int)