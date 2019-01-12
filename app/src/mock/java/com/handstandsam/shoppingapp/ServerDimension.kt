package com.handstandsam.shoppingapp

import com.handstandsam.shoppingapp.di.BaseNetworkGraph
import com.handstandsam.shoppingapp.di.NetworkGraph
import com.handstandsam.shoppingapp.mockdata.ProduceMockAccount
import com.handstandsam.shoppingapp.network.MockNetworkManager


fun MyAbstractApplication.serverDimensionNetworkGraph(): NetworkGraph {

    val networkConfig = NetworkConfigs.LOCALHOST

//          val mockAccount = VideoGameMockAccount()
    val mockAccount = ProduceMockAccount()

    MockNetworkManager(
        appContext = applicationContext,
        networkConfig = networkConfig,
        mockAccount = mockAccount
    )

    return BaseNetworkGraph(
        appContext = applicationContext,
        networkConfig = networkConfig
    )
}