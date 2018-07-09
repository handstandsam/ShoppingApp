package com.handstandsam.shoppingapp.network

import android.content.Context
import com.handstandsam.shoppingapp.di.NetworkConfig
import com.handstandsam.shoppingapp.mockaccount.Stubberator
import com.handstandsam.shoppingapp.mockdata.ProduceMockAccount
import com.handstandsam.shoppingapp.wiremock.WireMockManager


class NetworkManager(private val appContext: Context, private val networkConfig: NetworkConfig) {

    lateinit var stubberator: Stubberator

    fun init() {
        if (networkConfig.isWireMockServer) {
            stubberator = Stubberator(
                useLocalServer = networkConfig.isLocalhostServer
            )
//            stubberator.stubItAll(VideoGameMockAccount())
            stubberator.stubItAll(ProduceMockAccount())

            networkConfig.recordProxyEndpoint?.let {
                // Proxy and Record
                val wireMockManager = WireMockManager(appContext, networkConfig.port)
                wireMockManager.startProxyAndRecord(networkConfig.fullUrl)
            }

            if (networkConfig.loadAndPlaybackFileBasedMappings) {
                // Playback
                val wireMockManager = WireMockManager(appContext, networkConfig.port)
                wireMockManager.startPlayBack()
            }
        }
    }
}