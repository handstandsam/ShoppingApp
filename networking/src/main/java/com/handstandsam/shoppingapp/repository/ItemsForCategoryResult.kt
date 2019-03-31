package com.handstandsam.shoppingapp.repository

import com.handstandsam.shoppingapp.models.Item
import retrofit2.Response

sealed class ItemsForCategoryResult {
    data class Success(val items: List<Item>) : ItemsForCategoryResult()
    data class Failure(val networkErrorResponse: Response<List<Item>>? = null) :
        ItemsForCategoryResult()
}
