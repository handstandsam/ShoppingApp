package com.handstandsam.shoppingapp.di

import android.app.Application
import com.handstandsam.shoppingapp.debugDimensionInterceptors
import com.handstandsam.shoppingapp.features.home.ShoppingAppViewModelFactory
import com.handstandsam.shoppingapp.serverDimensionNetworkGraph
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

interface AppGraph {
    val sessionGraph: SessionGraph
    val networkGraph: NetworkGraph
    val viewModelFactory: ShoppingAppViewModelFactory
}

class AppGraphImpl(application: Application) : AppGraph {
    override val sessionGraph = SessionGraphImpl(application)

    override val networkGraph = application.serverDimensionNetworkGraph(debugDimensionInterceptors(application))

    private val appScope = CoroutineScope(Dispatchers.Default)

    override val viewModelFactory = ShoppingAppViewModelFactory(
        scope = appScope,
        sessionManager = sessionGraph.sessionManager,
        categoryRepo = networkGraph.categoryRepo,
        shoppingCart = sessionGraph.shoppingCart,
        itemRepo = networkGraph.itemRepo
    )
}