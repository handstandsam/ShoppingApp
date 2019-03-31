package com.handstandsam.shoppingapp.repository

import com.handstandsam.shoppingapp.models.Item

interface ItemRepo {

    suspend fun getItemsForCategory(categoryLabel: String): List<Item>?
}
