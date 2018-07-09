package com.handstandsam.shoppingapp

import android.app.Application
import com.handstandsam.shoppingapp.di.AppGraph
import com.handstandsam.shoppingapp.di.SessionGraphImpl
import timber.log.Timber

abstract class MyAbstractApplication : Application() {

    open val appGraph: AppGraph by lazy {
        AppGraph(
            sessionGraph = SessionGraphImpl(applicationContext),
            networkGraph = this.serverDimensionNetworkGraph()
        )
    }

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }

}

fun Application.appGraph(): AppGraph {
    return (this as MyAbstractApplication).appGraph
}