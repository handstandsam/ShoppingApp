package com.handstandsam.shoppingapp.repository

import com.handstandsam.shoppingapp.models.Category
import com.handstandsam.shoppingapp.network.ShoppingService

class NetworkCategoryRepo(private val shoppingService: ShoppingService) : CategoryRepo {

    override suspend fun getCategories(): List<Category> {
        return shoppingService.categories().await()
    }
}
