package com.handstandsam.shoppingapp

import com.handstandsam.shoppingapp.di.BaseNetworkGraph
import com.handstandsam.shoppingapp.di.NetworkGraph
import com.handstandsam.shoppingapp.di.OkHttpKtorClientBuilder
import com.handstandsam.shoppingapp.mockdata.ProduceMockAccount
import com.handstandsam.shoppingapp.models.NetworkConfig
import com.handstandsam.shoppingapp.network.MockNetworkManager

fun serverDimensionNetworkGraph(): NetworkGraph {

    val networkConfig = NetworkConfig()

    val mockAccount = ProduceMockAccount()

    MockNetworkManager(
        networkConfig = networkConfig,
        mockAccount = mockAccount
    )

    return BaseNetworkGraph(
        networkConfig = networkConfig,
        ktorClient = OkHttpKtorClientBuilder.createKtorClient()
    )
}