package com.handstandsam.shoppingapp.repository

import com.handstandsam.shoppingapp.models.LoginRequest
import com.handstandsam.shoppingapp.models.User
import com.handstandsam.shoppingapp.network.ShoppingService
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.request
import io.ktor.client.request.setBody
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode

class NetworkUserRepo(private val shoppingService: ShoppingService, private val ktorClient: HttpClient) : UserRepo {

    //    @POST("login")
//    fun login(@Body loginRequest: LoginRequest): Deferred<Response<User>>
    override suspend fun login(loginRequest: LoginRequest): NetworkResult<User> {
        val response2 = ktorClient.request("login") {
            method = HttpMethod.Get
            setBody(loginRequest)
        }

        if (response2.status == HttpStatusCode.OK) {
            val user = response2.body<User>()
            return NetworkResult.Success(user)

        }
        return NetworkResult.Failure()
    }
}
