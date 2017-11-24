package com.handstandsam.shoppingapp.features.home

import android.content.Context
import android.content.Intent
import com.handstandsam.shoppingapp.MyAbstractApplication
import com.handstandsam.shoppingapp.models.Category
import com.handstandsam.shoppingapp.repository.CategoryRepository
import com.handstandsam.shoppingapp.repository.SessionManager
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class HomePresenter(private val view: HomeActivity.HomeView) {

    private val applicationContext: Context = view.context.applicationContext

    @Inject
    lateinit var sessionManager: SessionManager

    @Inject
    lateinit var categoryRepository: CategoryRepository

    init {
        (applicationContext as MyAbstractApplication).appComponent.inject(this)
    }

    fun onResume(intent: Intent) {
        categoryRepository.getCategories().subscribe(object : SingleObserver<List<Category>> {
            override fun onSubscribe(d: Disposable) {

            }

            override fun onSuccess(categories: List<Category>) {
                view.showCategories(categories)
            }

            override fun onError(e: Throwable) {

            }
        })

        val currentUser = sessionManager!!.currentUser
        val welcomeStr = "Welcome back " + currentUser!!.firstname + " " + currentUser.lastname
        view.setWelcomeMessage(welcomeStr)
    }
}
