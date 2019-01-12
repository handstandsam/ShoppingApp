package com.handstandsam.shoppingapp

import com.handstandsam.shoppingapp.di.BaseNetworkGraph
import com.handstandsam.shoppingapp.di.NetworkGraph

fun MyAbstractApplication.serverDimensionNetworkGraph(): NetworkGraph {
    return BaseNetworkGraph(
        appContext = applicationContext,
        networkConfig = NetworkConfigs.S_3_LIVE_ENDPOINT
    )
}