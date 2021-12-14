package com.handstandsam.shoppingapp.models

data class NetworkConfig(
    val baseUrl: String = "http://localhost",
    val port: Int = 8080,
    val isMockServer: Boolean = true
) {
    val fullUrl by lazy {
        var fullUrl = "$baseUrl:$port"
        if (!baseUrl.endsWith("/")) {
            fullUrl += "/"
        }
        fullUrl
    }
}
