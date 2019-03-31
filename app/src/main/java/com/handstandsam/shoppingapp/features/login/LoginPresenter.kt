package com.handstandsam.shoppingapp.features.login

import com.handstandsam.shoppingapp.R
import com.handstandsam.shoppingapp.models.LoginRequest
import com.handstandsam.shoppingapp.preferences.UserPreferences
import com.handstandsam.shoppingapp.repository.NetworkResult
import com.handstandsam.shoppingapp.repository.SessionManager
import com.handstandsam.shoppingapp.repository.UserRepo
import com.handstandsam.shoppingapp.utils.exhaustive
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginPresenter(
    private val view: LoginActivity.LoginView,
    internal var sessionManager: SessionManager,
    internal var userPreferences: UserPreferences,
    internal var userRepo: UserRepo
) : CoroutineScope by CoroutineScope(Dispatchers.Main) {

    fun onResume() {

        if (sessionManager.isLoggedIn) {
            view.kickToHomeScreen()
        }

        //Nothing for now...
        val username = userPreferences.lastLoggedInUsername
        if (username != null) {
            view.username = username
        }

        view.setRememberMe(userPreferences.rememberMe)
    }

    fun loginClicked() {
        val rememberMe = view.isRememberMeChecked
        val username = view.username
        val password = view.password

        launch(Dispatchers.Main) {
            val userResult = userRepo.login(LoginRequest(username, password))
            when (userResult) {
                is NetworkResult.Success -> {
                    userPreferences.setRememberMe(rememberMe, view.username)
                    sessionManager.currentUser = userResult.body
                    view.startHomeActivity()
                }
                is NetworkResult.Failure -> {
                    view.showToast(R.string.invalid_username_or_password)
                }
            }.exhaustive
        }
    }
}
