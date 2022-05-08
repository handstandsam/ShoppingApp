package com.handstandsam.shoppingapp

import com.handstandsam.shoppingapp.di.BaseNetworkGraph
import com.handstandsam.shoppingapp.di.NetworkGraph
import com.handstandsam.shoppingapp.mockdata.ProduceMockAccount
import com.handstandsam.shoppingapp.models.NetworkConfig
import com.handstandsam.shoppingapp.network.MockNetworkManager
import okhttp3.Interceptor

fun serverDimensionNetworkGraph(interceptors: List<Interceptor> = listOf()): NetworkGraph {

    val networkConfig = NetworkConfig()

    val mockAccount = ProduceMockAccount()

    MockNetworkManager(
        networkConfig = networkConfig,
        mockAccount = mockAccount
    )

    return BaseNetworkGraph(
        networkConfig = networkConfig,
        interceptors = interceptors
    )
}