package com.handstandsam.shoppingapp.features.login

import com.handstandsam.shoppingapp.R
import com.handstandsam.shoppingapp.mockdata.ProduceMockAccount
import com.handstandsam.shoppingapp.models.LoginRequest
import com.handstandsam.shoppingapp.models.User
import com.handstandsam.shoppingapp.preferences.UserPreferences
import com.handstandsam.shoppingapp.repository.SessionManager
import com.handstandsam.shoppingapp.repository.UserRepo
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class LoginPresenter(private val view: LoginActivity.LoginView) {

    @Inject
    internal lateinit var sessionManager: SessionManager

    @Inject
    internal lateinit var userPreferences: UserPreferences

    @Inject
    internal lateinit var userRepo: UserRepo

    init {
        view.appComponent.inject(this)
    }

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
                sessionManager.currentUser = ProduceMockAccount().getUser()
                view.startHomeActivity()
            }

            override fun onError(e: Throwable) {
                view.showToast(R.string.invalid_username_or_password)
            }
        })
    }
}
