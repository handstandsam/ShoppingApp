package com.handstandsam.shoppingapp

import android.app.Application
import com.handstandsam.shoppingapp.di.BaseNetworkGraph
import com.handstandsam.shoppingapp.di.NetworkGraph
import com.handstandsam.shoppingapp.mockdata.ProduceMockAccount
import com.handstandsam.shoppingapp.models.NetworkConfig
import com.handstandsam.shoppingapp.network.MockNetworkManager


fun Application.serverDimensionNetworkGraph(): NetworkGraph {

    val networkConfig = NetworkConfig()

    val mockAccount = ProduceMockAccount()

    MockNetworkManager(
        appContext = applicationContext,
        networkConfig = networkConfig,
        mockAccount = mockAccount
    )

    return BaseNetworkGraph(
        networkConfig = networkConfig
    )
}