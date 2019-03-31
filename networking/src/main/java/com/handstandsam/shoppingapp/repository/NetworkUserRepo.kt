package com.handstandsam.shoppingapp.repository

import com.handstandsam.shoppingapp.models.LoginRequest
import com.handstandsam.shoppingapp.network.ShoppingService

class NetworkUserRepo(private val shoppingService: ShoppingService) : UserRepo {

    override suspend fun login(loginRequest: LoginRequest): UserResult {
        val response = shoppingService.login(loginRequest).await()
        if (response.isSuccessful) {
            val user = response.body()
            if (user != null) {
                return UserResult.Success(user)
            }
        }
        return UserResult.Failure(response)
    }
}
