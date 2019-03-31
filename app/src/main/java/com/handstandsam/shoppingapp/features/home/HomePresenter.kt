package com.handstandsam.shoppingapp.features.home

import android.content.Intent
import com.handstandsam.shoppingapp.repository.CategoryRepo
import com.handstandsam.shoppingapp.repository.SessionManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomePresenter(
    private val view: HomeActivity.HomeView,
    private val sessionManager: SessionManager,
    private val categoryRepo: CategoryRepo
) : CoroutineScope by CoroutineScope(Dispatchers.Main) {

    fun onResume(intent: Intent) {
        launch {
            val categories = categoryRepo.getCategories()
            view.showCategories(categories)
        }
//            .subscribe(object : SingleObserver<List<Category>> {
//            override fun onSubscribe(d: Disposable) {
//                Timber.d("onSubscribe")
//            }
//
//            override fun onSuccess(categories: List<Category>) {
//            }
//
//            override fun onError(e: Throwable) {
//                Timber.d("onError")
//
//            }
//        })

        val currentUser = sessionManager.currentUser
        val welcomeStr = "Welcome back " + currentUser?.firstname + " " + currentUser?.lastname
        view.setWelcomeMessage(welcomeStr)
    }
}
