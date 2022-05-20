package com.handstandsam.shoppingapp

import com.handstandsam.shoppingapp.di.BaseNetworkGraph
import com.handstandsam.shoppingapp.di.OkHttpKtorClientBuilder
import com.handstandsam.shoppingapp.models.LoginRequest
import com.handstandsam.shoppingapp.models.NetworkConfig
import com.handstandsam.shoppingapp.models.User
import com.handstandsam.shoppingapp.network.Response
import com.handstandsam.shoppingapp.repository.UserRepo

class LiveNetworkGraph : BaseNetworkGraph(
    networkConfig = NetworkConfig(
        baseUrl = "https://shopping-app.s3.amazonaws.com",
        isMockServer = false,
        port = 443
    ),
    ktorClient = OkHttpKtorClientBuilder.createKtorClient()
) {

    /**
     * Our S3 server can't support POST calls,
     * so we are just returning a mock for this call.
     */
    override val userRepo: UserRepo = object : UserRepo {
        override suspend fun login(loginRequest: LoginRequest): Response<User> {
            return Response.Success(
                User(
                    firstname = "Live",
                    lastname = "User"
                )
            )
        }
    }
}