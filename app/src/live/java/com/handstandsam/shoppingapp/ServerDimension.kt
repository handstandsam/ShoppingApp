package com.handstandsam.shoppingapp

import com.handstandsam.shoppingapp.di.NetworkGraph
import com.handstandsam.shoppingapp.di.NetworkGraphImpl
import com.handstandsam.shoppingapp.models.NetworkConfig

fun MyAbstractApplication.serverDimensionNetworkGraph(): NetworkGraph {
    return object : NetworkGraphImpl(applicationContext) {
        override val networkConfig: NetworkConfig by lazy { NetworkConfigs.S_3_LIVE_ENDPOINT }
    }
}