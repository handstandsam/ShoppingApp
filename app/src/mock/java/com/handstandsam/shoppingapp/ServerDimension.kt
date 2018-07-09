package com.handstandsam.shoppingapp

import com.handstandsam.shoppingapp.di.NetworkGraph
import com.handstandsam.shoppingapp.di.NetworkGraphImpl


fun MyAbstractApplication.serverDimensionNetworkGraph(): NetworkGraph {
    return NetworkGraphImpl(applicationContext)
}