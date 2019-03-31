package com.handstandsam.shoppingapp.features.home

import android.content.Intent
import com.handstandsam.shoppingapp.repository.CategoriesResult
import com.handstandsam.shoppingapp.repository.CategoryRepo
import com.handstandsam.shoppingapp.repository.SessionManager
import com.handstandsam.shoppingapp.utils.exhaustive
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

class HomePresenter(
    private val view: HomeActivity.HomeView,
    private val sessionManager: SessionManager,
    private val categoryRepo: CategoryRepo
) : CoroutineScope by CoroutineScope(Dispatchers.Main) {

    fun onResume(intent: Intent) {
        launch {
            val categoriesResult = categoryRepo.getCategories()
            when (categoriesResult) {
                is CategoriesResult.Success -> {
                    view.showCategories(categoriesResult.categories)
                }
                is CategoriesResult.Failure -> {
                    Timber.d("onError")
                }
            }.exhaustive
        }

        val currentUser = sessionManager.currentUser
        val welcomeStr = "Welcome back " + currentUser?.firstname + " " + currentUser?.lastname
        view.setWelcomeMessage(welcomeStr)
    }
}
