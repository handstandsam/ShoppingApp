package com.handstandsam.shoppingapp

import com.handstandsam.shoppingapp.di.NetworkGraph
import okhttp3.Interceptor

fun serverDimensionNetworkGraph(): NetworkGraph {
    return LiveNetworkGraph()
}