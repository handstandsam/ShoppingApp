package com.handstandsam.shoppingapp

import android.content.Context
import com.handstandsam.shoppingapp.di.NetworkConstants
import com.handstandsam.shoppingapp.mockaccount.Stubberator
import com.handstandsam.shoppingapp.mockdata.MockAccount
import com.handstandsam.shoppingapp.wiremock.WireMockManager


internal class NetworkConfig(var applicationContext: Context) {

    fun startNormally() {}

    fun stubLocalWireMock(mockAccount: MockAccount) {
        Stubberator(useLocalServer = true).stubItAll(mockAccount)
    }

    fun stubRemoteWireMock(mockAccount: MockAccount) {
        Stubberator(useLocalServer = false).stubItAll(mockAccount)
    }

    fun recordMappingsAndProxy(remoteBaseUrl: String) {
        // Record
        val wireMockManager =
            WireMockManager.Builder(applicationContext, NetworkConstants.LOCALHOST_PORT).build()
        wireMockManager.startProxyAndRecord(remoteBaseUrl)
    }


    fun playbackRecordedMappings() {
        // Playback
        val wireMockManager =
            WireMockManager.Builder(applicationContext, NetworkConstants.LOCALHOST_PORT).build()
        wireMockManager.startPlayBack()
    }
}
