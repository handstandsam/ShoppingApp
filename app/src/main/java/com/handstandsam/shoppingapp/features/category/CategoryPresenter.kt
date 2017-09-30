package com.handstandsam.shoppingapp.features.category

import android.content.Context
import android.content.Intent
import android.os.Bundle

import com.handstandsam.shoppingapp.MyAbstractApplication
import com.handstandsam.shoppingapp.models.Category
import com.handstandsam.shoppingapp.models.Item
import com.handstandsam.shoppingapp.repository.CategoryRepository
import com.handstandsam.shoppingapp.repository.ItemRepository
import com.handstandsam.shoppingapp.repository.SessionManager

import javax.inject.Inject

import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable
import timber.log.Timber

class CategoryPresenter(private val view: CategoryActivity.CategoryView) {

    private val applicationContext: Context = view.context

    @Inject
    lateinit internal var sessionManager: SessionManager

    @Inject
    lateinit internal var itemRepository: ItemRepository

    init {
        (applicationContext as MyAbstractApplication).appComponent.inject(this)
    }

    fun onResume(intent: Intent) {
        val extras = intent.extras
        val (label) = extras!!.get(BUNDLE_PARAM_CATEGORY) as Category
        view.setActionBarTitle(label!!)
        itemRepository.getItemsForCategory(label).subscribe(object : SingleObserver<List<Item>> {
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

        val BUNDLE_PARAM_CATEGORY = "category"
    }
}
