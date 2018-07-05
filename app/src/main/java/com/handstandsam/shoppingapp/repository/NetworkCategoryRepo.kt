package com.handstandsam.shoppingapp.repository

import com.handstandsam.shoppingapp.models.Category
import com.handstandsam.shoppingapp.network.ShoppingService
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class NetworkCategoryRepo(private val shoppingService: ShoppingService) : CategoryRepo {

    override fun getCategories(): Single<List<Category>> {
        return shoppingService
            .categories
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}
