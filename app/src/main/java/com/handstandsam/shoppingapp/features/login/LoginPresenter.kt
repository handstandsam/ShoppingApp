package com.handstandsam.shoppingapp.features.login

import com.handstandsam.shoppingapp.R
import com.handstandsam.shoppingapp.models.LoginRequest
import com.handstandsam.shoppingapp.preferences.UserPreferences
import com.handstandsam.shoppingapp.repository.SessionManager
import com.handstandsam.shoppingapp.repository.UserRepo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginPresenter(
    private val view: LoginActivity.LoginView,
    internal var sessionManager: SessionManager,
    internal var userPreferences: UserPreferences,
    internal var userRepo: UserRepo
) : CoroutineScope by CoroutineScope(Dispatchers.Default) {

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
            try {
                val user = userRepo.login(LoginRequest(username, password))
                userPreferences.setRememberMe(rememberMe, view.username)
                sessionManager.currentUser = user
                view.startHomeActivity()
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    view.showToast(R.string.invalid_username_or_password)
                }
            }
        }

    }
}
