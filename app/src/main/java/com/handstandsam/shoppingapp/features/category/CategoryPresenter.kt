package com.handstandsam.shoppingapp.features.category

import android.content.Intent
import com.handstandsam.shoppingapp.models.Category
import com.handstandsam.shoppingapp.repository.ItemRepo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CategoryPresenter(
    private val view: CategoryActivity.CategoryView,
    private var itemRepo: ItemRepo
) : CoroutineScope by CoroutineScope(Dispatchers.Main) {

    fun onResume(intent: Intent) {
        val extras = intent.extras
        val (label) = extras!!.get(BUNDLE_PARAM_CATEGORY) as Category
        view.setActionBarTitle(label)
        launch {
            val items = itemRepo.getItemsForCategory(label)
            items?.let {
                view.showItems(items)
            }
        }
//            .subscribe(object : SingleObserver<List<Item>> {
//            override fun onSubscribe(d: Disposable) {
//
//            }
//
//            override fun onSuccess(items: List<Item>) {
//
//            }
//
//            override fun onError(e: Throwable) {
//                Timber.w("Networking Error", e)
//                Timber.w(e.message)
//                Timber.w(e)
//                view.showNetworkError(e.message)
//            }
//        })
    }

    companion object {
        const val BUNDLE_PARAM_CATEGORY = "category"
    }
}
