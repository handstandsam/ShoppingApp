package com.handstandsam.shoppingapp.features.category

import android.content.Intent
import com.handstandsam.shoppingapp.models.Category
import com.handstandsam.shoppingapp.models.Item
import com.handstandsam.shoppingapp.repository.ItemRepo
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable
import timber.log.Timber

class CategoryPresenter(
    private val view: CategoryActivity.CategoryView,
    private var itemRepo: ItemRepo
) {

    fun onResume(intent: Intent) {
        val extras = intent.extras
        val (label) = extras!!.get(BUNDLE_PARAM_CATEGORY) as Category
        view.setActionBarTitle(label)
        itemRepo.getItemsForCategory(label).subscribe(object : SingleObserver<List<Item>> {
            override fun onSubscribe(d: Disposable) {

            }

            override fun onSuccess(items: List<Item>) {
                view.showItems(items)
            }

            override fun onError(e: Throwable) {
                Timber.w("Networking Error", e)
                Timber.w(e.message)
                Timber.w(e)
                view.showNetworkError(e.message)
            }
        })
    }

    companion object {
        const val BUNDLE_PARAM_CATEGORY = "category"
    }
}
