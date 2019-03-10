package com.handstandsam.shoppingapp.repository

import com.handstandsam.shoppingapp.models.LoginRequest
import com.handstandsam.shoppingapp.models.User
import io.reactivex.Single

interface UserRepo {
    fun login(loginRequest: LoginRequest): Single<User>
}
