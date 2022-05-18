package com.handstandsam.shoppingapp.repository

import com.handstandsam.shoppingapp.di.BaseNetworkGraph
import com.handstandsam.shoppingapp.models.LoginRequest
import com.handstandsam.shoppingapp.models.NetworkConfig
import com.handstandsam.shoppingapp.models.User
import io.ktor.client.call.body
import io.ktor.client.request.request
import io.ktor.client.request.setBody
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import okhttp3.OkHttpClient
import org.junit.Test

class NetworkUserRepoTest {

//    val ktorClient = BaseNetworkGraph.createKtorClient(
//        OkHttpClient.Builder().apply {
//        }.build()
//    )
//
//    val networkGraph = BaseNetworkGraph(NetworkConfig(), ktorClient)
//
//    @Test
//    fun login() {
//
//        val response2 = ktorClient.request("/login") {
//            method = HttpMethod.Get
//            setBody(loginRequest)
//        }
//
//        if (response2.status == HttpStatusCode.OK) {
//            val user = response2.body<User>()
//            return NetworkResult.Success(user)
//
//        }
//        return NetworkResult.Failure()
    }
}
