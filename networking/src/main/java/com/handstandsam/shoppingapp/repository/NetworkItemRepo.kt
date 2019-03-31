package com.handstandsam.shoppingapp.repository

import com.handstandsam.shoppingapp.models.Item
import com.handstandsam.shoppingapp.network.ShoppingService

class NetworkItemRepo(private val shoppingService: ShoppingService) : ItemRepo {

    override suspend fun getItemsForCategory(categoryLabel: String): List<Item>? {
        return shoppingService.getItemsForCategory(categoryLabel).await()
    }
}
