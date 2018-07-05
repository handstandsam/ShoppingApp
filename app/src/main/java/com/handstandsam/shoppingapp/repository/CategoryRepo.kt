package com.handstandsam.shoppingapp.repository

import com.handstandsam.shoppingapp.models.Category
import io.reactivex.Single

interface CategoryRepo {
    fun getCategories(): Single<List<Category>>
}
