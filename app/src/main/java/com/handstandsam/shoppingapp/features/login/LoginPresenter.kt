package com.handstandsam.shoppingapp.features.login

import com.handstandsam.shoppingapp.R
import com.handstandsam.shoppingapp.models.LoginRequest
import com.handstandsam.shoppingapp.models.User
import com.handstandsam.shoppingapp.preferences.UserPreferences
import com.handstandsam.shoppingapp.repository.SessionManager
import com.handstandsam.shoppingapp.repository.UserRepo
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable

class LoginPresenter(
    private val view: LoginActivity.LoginView,
    internal var sessionManager: SessionManager,
    internal var userPreferences: UserPreferences,
    internal var userRepo: UserRepo
) {

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
        userRepo.login(LoginRequest(username, password)).subscribe(object : SingleObserver<User> {
            override fun onSubscribe(d: Disposable) {

            }

            override fun onSuccess(user: User) {
                userPreferences.setRememberMe(rememberMe, view.username)
                sessionManager.currentUser = user
                view.startHomeActivity()
            }

            override fun onError(e: Throwable) {
                view.showToast(R.string.invalid_username_or_password)
            }
        })
    }
}
