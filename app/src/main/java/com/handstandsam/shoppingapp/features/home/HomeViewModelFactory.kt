package com.handstandsam.shoppingapp.features.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.handstandsam.shoppingapp.repository.CategoryRepo
import com.handstandsam.shoppingapp.repository.SessionManager
import kotlinx.coroutines.CoroutineScope

class HomeViewModelFactory(
    private val scope: CoroutineScope,
    private val sessionManager: SessionManager,
    private val categoryRepo: CategoryRepo
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(
                scope = scope,
                sessionManager = sessionManager,
                categoryRepo = categoryRepo
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}