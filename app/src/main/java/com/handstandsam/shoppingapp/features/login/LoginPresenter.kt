package com.handstandsam.shoppingapp.features.login

import com.handstandsam.shoppingapp.R
import com.handstandsam.shoppingapp.models.LoginRequest
import com.handstandsam.shoppingapp.models.User
import com.handstandsam.shoppingapp.network.Response
import com.handstandsam.shoppingapp.preferences.UserPreferences
import com.handstandsam.shoppingapp.repository.SessionManager
import com.handstandsam.shoppingapp.repository.UserRepo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class LoginPresenter(
    private val view: LoginActivity.LoginView,
    internal var sessionManager: SessionManager,
    internal var userPreferences: UserPreferences,
    internal var userRepo: UserRepo,
    scope: CoroutineScope
) : CoroutineScope by scope {

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

        launch {
            val userResult = userRepo.login(LoginRequest(username, password))
            when (userResult) {
                is Response.Success<User> -> {
                    userPreferences.setLastLoggedInUsername(rememberMe, view.username)
                    sessionManager.updateCurrentUser(userResult.body)
                    view.startHomeActivity()
                }
                is Response.Failure -> {
                    view.showToast(R.string.invalid_username_or_password)
                }
            }
        }
    }
}
