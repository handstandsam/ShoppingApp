package com.handstandsam.shoppingapp.repository

import com.handstandsam.shoppingapp.models.LoginRequest
import com.handstandsam.shoppingapp.models.User

interface UserRepo {
    suspend fun login(loginRequest: LoginRequest): NetworkResult<User>
}
