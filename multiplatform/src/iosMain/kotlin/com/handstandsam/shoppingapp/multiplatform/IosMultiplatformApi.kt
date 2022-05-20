package com.handstandsam.shoppingapp.multiplatform

import com.handstandsam.shoppingapp.di.NetworkGraph

class IosMultiplatformApi {
    private val ktorHttpClient = KtorHttpClient()

    val networkGraph: NetworkGraph = ktorHttpClient.networkGraph
}