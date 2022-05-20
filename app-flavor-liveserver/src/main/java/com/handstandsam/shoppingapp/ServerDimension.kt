package com.handstandsam.shoppingapp

import com.handstandsam.shoppingapp.di.NetworkGraph

fun serverDimensionNetworkGraph(): NetworkGraph {
    return LiveNetworkGraph()
}