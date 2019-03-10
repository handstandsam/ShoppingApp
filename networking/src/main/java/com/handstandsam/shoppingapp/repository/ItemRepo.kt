package com.handstandsam.shoppingapp.repository

import com.handstandsam.shoppingapp.models.Item
import io.reactivex.Single

interface ItemRepo {

    fun getItemsForCategory(categoryLabel: String): Single<List<Item>>
}
