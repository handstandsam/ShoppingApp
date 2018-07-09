package com.handstandsam.shoppingapp

import android.app.Application
import com.handstandsam.shoppingapp.di.AppGraph
import com.handstandsam.shoppingapp.di.NetworkGraphImpl
import com.handstandsam.shoppingapp.di.SessionGraphImpl
import com.handstandsam.shoppingapp.mockdata.ProduceMockAccount
import timber.log.Timber

abstract class MyAbstractApplication : Application() {

    lateinit var appGraph: AppGraph

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        appGraph = createAppGraph()
        localWireMock()
        //        record();
        //        playback();
        //        connectToLaptop();
    }


    protected open fun createAppGraph(): AppGraph {
        return AppGraph(
            sessionGraph = SessionGraphImpl(applicationContext),
            networkGraph = NetworkGraphImpl(applicationContext)
        )
    }

    //    fun startNormally() {
//        appComponent = createAppGraph(NetworkModule.S3_ENDPOINT)
//        val networkConfig = NetworkConfig(this)
//        networkConfig.startNormally()
//    }
//
//    fun record() {
//        appComponent = createAppGraph("http://localhost:8080")
//        val networkConfig = NetworkConfig(this)
//        networkConfig.recordMappingsAndProxy(NetworkModule.S3_ENDPOINT)
//    }
//
//    fun playback() {
//        appComponent = createAppGraph("http://localhost:8080")
//        val networkConfig = NetworkConfig(this)
//        networkConfig.playbackRecordedMappings()
//    }
//
//    fun connectToLaptop() {
//        appComponent = createAppGraph("http://10.0.2.2:8080")
//        val networkConfig = NetworkConfig(this)
//        networkConfig.startNormally()
//    }
//
    fun localWireMock() {
        val networkConfig = NetworkConfig(this)
//        networkConfig.stubLocalWireMock(VideoGameMockAccount())
        networkConfig.stubLocalWireMock(ProduceMockAccount())
    }
}

fun Application.appGraph(): AppGraph {
    return (this as MyAbstractApplication).appGraph
}
