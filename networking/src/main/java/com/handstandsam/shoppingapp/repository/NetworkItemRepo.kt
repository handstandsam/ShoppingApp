package com.handstandsam.shoppingapp.repository

import com.handstandsam.shoppingapp.models.Item
import com.handstandsam.shoppingapp.network.ShoppingService
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class NetworkItemRepo(private val shoppingService: ShoppingService) : ItemRepo {

    override fun getItemsForCategory(categoryLabel: String): Single<List<Item>> {
        return shoppingService
            .getItemsForCategory(categoryLabel)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}
