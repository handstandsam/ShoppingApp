package com.handstandsam.shoppingapp.repository

import com.handstandsam.shoppingapp.models.LoginRequest
import com.handstandsam.shoppingapp.models.User
import com.handstandsam.shoppingapp.network.Response

interface UserRepo {
    suspend fun login(loginRequest: LoginRequest): Response<User>
}
