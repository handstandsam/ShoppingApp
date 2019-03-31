package com.handstandsam.shoppingapp.repository

interface CategoryRepo {
    suspend fun getCategories(): CategoriesResult
}
