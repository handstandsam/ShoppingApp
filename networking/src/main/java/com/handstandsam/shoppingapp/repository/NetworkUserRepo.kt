package com.handstandsam.shoppingapp.repository

import com.handstandsam.shoppingapp.models.LoginRequest
import com.handstandsam.shoppingapp.models.User
import com.handstandsam.shoppingapp.network.ShoppingService

class NetworkUserRepo(private val shoppingService: ShoppingService) : UserRepo {

    override suspend fun login(loginRequest: LoginRequest): User {
        return shoppingService.login(loginRequest).await()
    }
}
