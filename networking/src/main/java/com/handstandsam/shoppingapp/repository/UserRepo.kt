package com.handstandsam.shoppingapp.repository

import com.handstandsam.shoppingapp.models.LoginRequest

interface UserRepo {
    suspend fun login(loginRequest: LoginRequest): UserResult
}
