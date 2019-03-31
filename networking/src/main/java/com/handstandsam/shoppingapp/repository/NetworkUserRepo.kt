package com.handstandsam.shoppingapp.repository

import com.handstandsam.shoppingapp.models.LoginRequest
import com.handstandsam.shoppingapp.models.User
import com.handstandsam.shoppingapp.network.ShoppingService

class NetworkUserRepo(private val shoppingService: ShoppingService) : UserRepo {

    override suspend fun login(loginRequest: LoginRequest): NetworkResult<User> {
        val response = shoppingService.login(loginRequest).await()
        if (response.isSuccessful) {
            val user = response.body()
            if (user != null) {
                return NetworkResult.Success(user)
            }
        }
        return NetworkResult.Failure(response)
    }
}
