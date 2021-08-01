package com.handstandsam.shoppingapp.features.home

import android.content.Intent
import com.handstandsam.shoppingapp.repository.CategoryRepo
import com.handstandsam.shoppingapp.repository.NetworkResult
import com.handstandsam.shoppingapp.repository.SessionManager
import com.handstandsam.shoppingapp.utils.exhaustive
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

class HomePresenter(
    private val view: HomeActivity.HomePresenter,
    private val sessionManager: SessionManager,
    private val categoryRepo: CategoryRepo,
    scope: CoroutineScope
) : CoroutineScope by scope {

    fun onResume(intent: Intent) {
        launch {
            val categoriesResult = withContext(Dispatchers.Default) {
                categoryRepo.getCategories()
            }
            when (categoriesResult) {
                is NetworkResult.Success -> {
                    view.showCategories(categoriesResult.body)
                }
                is NetworkResult.Failure -> {
                    Timber.d("onError")
                }
            }.exhaustive
        }


        val currentUser = sessionManager.currentUser
        val welcomeStr = "Welcome back " + currentUser?.firstname + " " + currentUser?.lastname
        view.setWelcomeMessage(welcomeStr)
    }
}
