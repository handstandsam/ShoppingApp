package com.handstandsam.shoppingapp

import android.app.Application
import android.content.Context
import com.handstandsam.shoppingapp.di.AppGraph
import com.handstandsam.shoppingapp.di.SessionGraphImpl

class MyApplication : Application() {

    val appGraph: AppGraph by lazy {
        AppGraph(
            sessionGraph = SessionGraphImpl(applicationContext),
            networkGraph = this.serverDimensionNetworkGraph(debugDimensionInterceptors(this))
        )
    }

    override fun onCreate() {
        super.onCreate()
        appGraph // Touch the graph to ensure it initializes during onCreate()
        this.debugDimensionOnCreate()
    }

}

fun Context.graph(): AppGraph {
    return (this.applicationContext as MyApplication).appGraph
}