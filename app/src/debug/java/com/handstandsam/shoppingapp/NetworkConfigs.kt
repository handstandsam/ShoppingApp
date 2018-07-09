package com.handstandsam.shoppingapp

import com.handstandsam.shoppingapp.models.NetworkConfig

object NetworkConfigs {
    val LOCALHOST: NetworkConfig = NetworkConfig()
    val LAPTOP: NetworkConfig = NetworkConfig(baseUrl = "http://10.0.2.2:8080")
    val S_3_LIVE_ENDPOINT: NetworkConfig =
        NetworkConfig(
            baseUrl = "https://shopping-app.s3.amazonaws.com",
            isWireMockServer = false,
            port = 443
        )
    val RECORD_S_3_LIVE_ENDPOINT: NetworkConfig =
        NetworkConfig(recordProxyEndpoint = "https://shopping-app.s3.amazonaws.com")
    val PLAYBACK_FILE_BASED_MAPPINGS: NetworkConfig =
        NetworkConfig(loadAndPlaybackFileBasedMappings = true)
}