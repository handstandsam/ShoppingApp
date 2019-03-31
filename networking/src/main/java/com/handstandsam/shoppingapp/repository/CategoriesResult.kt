package com.handstandsam.shoppingapp.repository

import com.handstandsam.shoppingapp.models.Category
import retrofit2.Response

sealed class CategoriesResult {
    data class Success(val categories: List<Category>) : CategoriesResult()
    data class Failure(val errorResponse: Response<List<Category>>) : CategoriesResult()
}
