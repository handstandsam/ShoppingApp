package com.handstandsam.shoppingapp

import android.content.Context
import com.handstandsam.shoppingapp.di.BaseNetworkGraph
import com.handstandsam.shoppingapp.models.LoginRequest
import com.handstandsam.shoppingapp.models.NetworkConfig
import com.handstandsam.shoppingapp.models.User
import com.handstandsam.shoppingapp.repository.UserRepo

class LiveNetworkGraph(appContext: Context) : BaseNetworkGraph(
    networkConfig = NetworkConfig(
        baseUrl = "https://shopping-app.s3.amazonaws.com",
        isWireMockServer = false,
        port = 443
    )
) {

    /**
     * Our S3 server can't support POST calls,
     * so we are just returning a mock for this call.
     */
    override val userRepo: UserRepo =
        object : UserRepo {
            override suspend fun login(loginRequest: LoginRequest): User {
                return User(
                    firstname = "Live",
                    lastname = "User"
                )
            }
        }
}