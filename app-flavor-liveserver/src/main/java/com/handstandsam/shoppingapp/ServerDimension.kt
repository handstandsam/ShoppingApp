package com.handstandsam.shoppingapp

import android.app.Application
import com.handstandsam.shoppingapp.di.NetworkGraph
import okhttp3.Interceptor

fun Application.serverDimensionNetworkGraph(interceptors: List<Interceptor>): NetworkGraph {
    return LiveNetworkGraph(interceptors)
}