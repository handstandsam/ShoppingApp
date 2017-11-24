package com.handstandsam.shoppingapp.repository

import android.content.Context
import com.handstandsam.shoppingapp.MyAbstractApplication
import com.handstandsam.shoppingapp.models.Category
import com.handstandsam.shoppingapp.network.ShoppingService
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class CategoryRepository(applicationContext: Context) {

    @Inject
    lateinit var shoppingService: ShoppingService

    init {
        (applicationContext as MyAbstractApplication)
                .appComponent
                .inject(this)
    }

    fun getCategories(): Single<List<Category>> {
        return shoppingService
                .categories
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }
}
