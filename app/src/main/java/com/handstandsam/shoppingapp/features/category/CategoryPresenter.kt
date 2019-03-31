package com.handstandsam.shoppingapp.features.category

import android.content.Intent
import com.handstandsam.shoppingapp.models.Category
import com.handstandsam.shoppingapp.repository.ItemRepo
import com.handstandsam.shoppingapp.repository.ItemsForCategoryResult
import com.handstandsam.shoppingapp.utils.exhaustive
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

class CategoryPresenter(
    private val view: CategoryActivity.CategoryView,
    private var itemRepo: ItemRepo
) : CoroutineScope by CoroutineScope(Dispatchers.Main) {

    fun onResume(intent: Intent) {
        val extras = intent.extras
        val (label) = extras!!.get(BUNDLE_PARAM_CATEGORY) as Category
        view.setActionBarTitle(label)
        launch {
            val itemsResult = itemRepo.getItemsForCategory(label)
            when (itemsResult) {
                is ItemsForCategoryResult.Success -> {
                    view.showItems(itemsResult.items)
                }
                is ItemsForCategoryResult.Failure -> {
                    Timber.w("Networking Error", itemsResult.networkErrorResponse)
                    view.showNetworkError(itemsResult.networkErrorResponse.toString())
                }
            }.exhaustive
        }
    }

    companion object {
        const val BUNDLE_PARAM_CATEGORY = "category"
    }
}
