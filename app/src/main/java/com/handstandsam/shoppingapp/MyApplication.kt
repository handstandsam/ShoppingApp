package com.handstandsam.shoppingapp

import android.app.Application
import android.content.Context
import com.handstandsam.shoppingapp.di.AppGraph
import com.handstandsam.shoppingapp.di.SessionGraphImpl

class MyApplication : Application() {

    open val appGraph: AppGraph by lazy {
        AppGraph(
            sessionGraph = SessionGraphImpl(applicationContext),
            networkGraph = this.serverDimensionNetworkGraph()
        )
    }

    override fun onCreate() {
        super.onCreate()
        this.debugDimensionInitializeLogging()
        appGraph
    }

}

fun Context.graph(): AppGraph {
    return (this.applicationContext as MyApplication).appGraph
}