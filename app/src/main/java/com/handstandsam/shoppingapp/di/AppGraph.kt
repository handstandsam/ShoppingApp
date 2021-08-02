package com.handstandsam.shoppingapp.di

import android.app.Application
import android.content.Context
import com.handstandsam.shoppingapp.debugDimensionInterceptors
import com.handstandsam.shoppingapp.features.home.HomeViewModelFactory
import com.handstandsam.shoppingapp.serverDimensionNetworkGraph
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

interface AppGraph {

    val sessionGraph: SessionGraph
    val networkGraph: NetworkGraph
    val homeViewModelFactory: HomeViewModelFactory
}

class AppGraphImpl(application: Application) : AppGraph {
    override val sessionGraph = SessionGraphImpl(application)

    override val networkGraph = application.serverDimensionNetworkGraph(debugDimensionInterceptors(application))

    private val appScope = CoroutineScope(Dispatchers.Default)

    override val homeViewModelFactory = HomeViewModelFactory(
        scope = appScope,
        sessionManager = sessionGraph.sessionManager,
        categoryRepo = networkGraph.categoryRepo
    )
}