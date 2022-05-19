package com.handstandsam.shoppingapp.repository

import com.handstandsam.shoppingapp.models.Item
import com.handstandsam.shoppingapp.network.Response

interface ItemRepo {
    suspend fun getItemsForCategory(categoryLabel: String): Response<List<Item>>
}
