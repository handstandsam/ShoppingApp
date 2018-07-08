package com.handstandsam.shoppingapp

import com.handstandsam.shoppingapp.di.AppGraph
import com.handstandsam.shoppingapp.di.DebugNetworkGraph
import com.handstandsam.shoppingapp.di.SessionGraphImpl
import timber.log.Timber

class MyApplication : MyAbstractApplication() {

    override fun onCreate() {
        Timber.plant(Timber.DebugTree())
        super.onCreate()
    }

    override fun createAppComponent(baseUrl: String): AppGraph {
        return AppGraph(
            sessionGraph = SessionGraphImpl(applicationContext),
            networkGraph = DebugNetworkGraph(applicationContext, baseUrl)
        )
    }
}
