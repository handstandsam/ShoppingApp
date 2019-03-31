package com.handstandsam.shoppingapp.repository

import com.handstandsam.shoppingapp.models.Category

interface CategoryRepo {
    suspend fun getCategories(): List<Category>
}
