package com.handstandsam.shoppingapp

import android.app.Application
import com.handstandsam.shoppingapp.di.AppComponent
import com.handstandsam.shoppingapp.di.NetworkModule
import com.handstandsam.shoppingapp.mockdata.ProduceMockAccount

abstract class MyAbstractApplication : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        localWireMock()
        //        record();
        //        playback();
        //        connectToLaptop();
    }


    protected abstract fun createAppComponent(endpoint: String): AppComponent

    fun startNormally() {
        appComponent = createAppComponent(NetworkModule.S3_ENDPOINT)
        val networkConfig = NetworkConfig(this)
        networkConfig.startNormally()
    }

    fun record() {
        appComponent = createAppComponent("http://localhost:8080")
        val networkConfig = NetworkConfig(this)
        networkConfig.recordMappingsAndProxy(NetworkModule.S3_ENDPOINT)
    }

    fun playback() {
        appComponent = createAppComponent("http://localhost:8080")
        val networkConfig = NetworkConfig(this)
        networkConfig.playbackRecordedMappings()
    }

    fun connectToLaptop() {
        appComponent = createAppComponent("http://10.0.2.2:8080")
        val networkConfig = NetworkConfig(this)
        networkConfig.startNormally()
    }

    fun localWireMock() {
        val endpoint: String

        endpoint = "http://localhost:8080" //LOCALHOST_ENDPOINT
        appComponent = createAppComponent(endpoint)

        val networkConfig = NetworkConfig(this)
//        networkConfig.stubLocalWireMock(VideoGameMockAccount())
        networkConfig.stubLocalWireMock(ProduceMockAccount())
    }

}
