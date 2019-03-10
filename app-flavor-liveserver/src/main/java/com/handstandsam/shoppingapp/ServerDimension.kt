package com.handstandsam.shoppingapp

import android.app.Application
import com.handstandsam.shoppingapp.di.NetworkGraph

fun Application.serverDimensionNetworkGraph(): NetworkGraph {
    return LiveNetworkGraph(applicationContext)
}