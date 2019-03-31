package com.handstandsam.shoppingapp.repository

import com.handstandsam.shoppingapp.network.ShoppingService

class NetworkCategoryRepo(private val shoppingService: ShoppingService) : CategoryRepo {

    override suspend fun getCategories(): CategoriesResult {
        val response = shoppingService.categories().await()
        if (response.isSuccessful) {
            val categories = response.body()
            if (categories != null) {
                return CategoriesResult.Success(categories)
            }
        }
        return CategoriesResult.Failure(response)

    }
}
