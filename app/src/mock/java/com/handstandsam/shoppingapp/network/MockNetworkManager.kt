package com.handstandsam.shoppingapp.network

import android.content.Context
import com.handstandsam.shoppingapp.mockaccount.Stubberator
import com.handstandsam.shoppingapp.mockdata.MockAccount
import com.handstandsam.shoppingapp.models.NetworkConfig
import com.handstandsam.shoppingapp.wiremock.WireMockManager


class MockNetworkManager(
    private val appContext: Context,
    private val networkConfig: NetworkConfig,
    mockAccount: MockAccount
) {

    lateinit var stubberator: Stubberator

    private fun init(mockAccount: MockAccount) {
        if (networkConfig.isWireMockServer) {
            stubberator = Stubberator(
                useLocalServer = networkConfig.isLocalhostServer
            )
            stubberator.stubItAll(mockAccount)

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

    init {
        init(mockAccount)
    }
}