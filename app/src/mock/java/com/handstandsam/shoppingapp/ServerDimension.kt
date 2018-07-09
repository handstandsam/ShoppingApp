package com.handstandsam.shoppingapp

import com.handstandsam.shoppingapp.di.NetworkGraph
import com.handstandsam.shoppingapp.di.NetworkGraphImpl
import com.handstandsam.shoppingapp.mockdata.ProduceMockAccount
import com.handstandsam.shoppingapp.network.MockNetworkManager


fun MyAbstractApplication.serverDimensionNetworkGraph(): NetworkGraph {
    return object : NetworkGraphImpl(applicationContext) {
        init {
//          val mockAccount = VideoGameMockAccount()
            val mockAccount = ProduceMockAccount()
            MockNetworkManager(applicationContext, networkConfig).init(mockAccount)
        }

    }
}