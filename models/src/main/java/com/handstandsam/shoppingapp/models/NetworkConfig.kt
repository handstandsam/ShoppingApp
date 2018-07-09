package com.handstandsam.shoppingapp.models

data class NetworkConfig(
    val baseUrl: String = "http://localhost",
    val recordProxyEndpoint: String? = null,
    val loadAndPlaybackFileBasedMappings: Boolean = false,
    val port: Int = 8080,
    val isWireMockServer: Boolean = true
) {
    val fullUrl by lazy {
        var fullUrl = "$baseUrl:$port"
        if (!baseUrl.endsWith("/")) {
            fullUrl += "/"
        }
        fullUrl
    }

    val isLocalhostServer by lazy {
        baseUrl.contains("//127.0.0.1") || baseUrl.contains("//localhost")
    }
}
