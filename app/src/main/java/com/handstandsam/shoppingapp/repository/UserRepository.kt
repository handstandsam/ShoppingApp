package com.handstandsam.shoppingapp.repository

import com.handstandsam.shoppingapp.models.LoginRequest
import com.handstandsam.shoppingapp.models.User
import com.handstandsam.shoppingapp.network.ShoppingService
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class UserRepository(var shoppingService: ShoppingService) {

    internal var user: User? = null

    fun save(user: User) {
        Timber.d("Saving User: " + user)
        this.user = user
    }

    fun login(loginRequest: LoginRequest): Single<User> {
        return shoppingService
                .login(loginRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }
}
