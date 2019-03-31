package com.handstandsam.shoppingapp.repository

import com.handstandsam.shoppingapp.models.Category
import com.handstandsam.shoppingapp.network.ShoppingService

class NetworkCategoryRepo(private val shoppingService: ShoppingService) : CategoryRepo {

    override suspend fun getCategories(): NetworkResult<List<Category>> {
        val response = shoppingService.categories().await()
        if (response.isSuccessful) {
            val categories = response.body()
            if (categories != null) {
                return NetworkResult.Success(categories)
            }
        }
        return NetworkResult.Failure(response)

    }
}
