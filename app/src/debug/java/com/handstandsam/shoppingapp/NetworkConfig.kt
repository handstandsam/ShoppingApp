package com.handstandsam.shoppingapp

import android.content.Context
import com.handstandsam.shoppingapp.di.NetworkModule
import com.handstandsam.shoppingapp.mockaccount.Stubberator
import com.handstandsam.shoppingapp.mockdata.MockAccount
import com.handstandsam.shoppingapp.wiremock.WireMockManager


internal class NetworkConfig(var applicationContext: Context) {

    fun startNormally() {}

    fun stubLocalWireMock(mockAccount: MockAccount) {
        NetworkModule.USE_LOCAL_SERVER = true
        Stubberator(applicationContext).stubItAll(mockAccount)
    }

    fun stubRemoteWireMock(mockAccount: MockAccount) {
        NetworkModule.USE_LOCAL_SERVER = false
        Stubberator(applicationContext).stubItAll(mockAccount)
    }


    fun recordMappingsAndProxy(remoteBaseUrl: String) {
        // Record
        val wireMockManager = WireMockManager.Builder(applicationContext, NetworkModule.LOCALHOST_PORT).build()
        wireMockManager.startProxyAndRecord(remoteBaseUrl)
    }


    fun playbackRecordedMappings() {
        // Playback
        val wireMockManager = WireMockManager.Builder(applicationContext, NetworkModule.LOCALHOST_PORT).build()
        wireMockManager.startPlayBack()
    }
}
