package com.handstandsam.shoppingapp.repository

import com.handstandsam.shoppingapp.models.Category
import com.handstandsam.shoppingapp.network.Response

interface CategoryRepo {
    suspend fun getCategories(): Response<List<Category>>
}
