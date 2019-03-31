package com.handstandsam.shoppingapp.repository

interface ItemRepo {
    suspend fun getItemsForCategory(categoryLabel: String): ItemsForCategoryResult
}
