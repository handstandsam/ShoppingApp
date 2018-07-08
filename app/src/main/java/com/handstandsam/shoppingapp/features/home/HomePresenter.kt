package com.handstandsam.shoppingapp.features.home

import android.content.Intent
import com.handstandsam.shoppingapp.models.Category
import com.handstandsam.shoppingapp.repository.CategoryRepo
import com.handstandsam.shoppingapp.repository.SessionManager
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable

class HomePresenter(
    private val view: HomeActivity.HomeView,
    private val sessionManager: SessionManager,
    private val categoryRepo: CategoryRepo
) {

    fun onResume(intent: Intent) {
        categoryRepo.getCategories().subscribe(object : SingleObserver<List<Category>> {
            override fun onSubscribe(d: Disposable) {

            }

            override fun onSuccess(categories: List<Category>) {
                view.showCategories(categories)
            }

            override fun onError(e: Throwable) {

            }
        })

        val currentUser = sessionManager.currentUser
        val welcomeStr = "Welcome back " + currentUser!!.firstname + " " + currentUser.lastname
        view.setWelcomeMessage(welcomeStr)
    }
}
