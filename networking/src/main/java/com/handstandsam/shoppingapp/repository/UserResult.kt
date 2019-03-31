package com.handstandsam.shoppingapp.repository

import com.handstandsam.shoppingapp.models.User
import retrofit2.Response

sealed class UserResult {
    data class Success(val user: User) : UserResult()
    data class Failure(val errorResponse: Response<User>) : UserResult()
}
