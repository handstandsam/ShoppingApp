package com.handstandsam.shoppingapp.repository

import com.handstandsam.shoppingapp.models.Item

class NetworkItemRepo(private val shoppingService: ShoppingService) : ItemRepo {

    override suspend fun getItemsForCategory(categoryLabel: String): NetworkResult<List<Item>> {
//        category/{categoryName}/items
        val response = shoppingService.getItemsForCategory(categoryLabel).await()
        if (response.isSuccessful) {
            val items = response.body()
            if (items != null) {
                return NetworkResult.Success(items)
            }
        }
        return NetworkResult.Failure()
    }
}
