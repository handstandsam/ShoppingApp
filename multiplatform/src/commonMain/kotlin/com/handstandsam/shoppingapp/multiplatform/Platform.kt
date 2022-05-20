package com.handstandsam.shoppingapp.multiplatform

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.HttpClientEngineFactory

expect class Platform() {
    val platform: String
    val ktorHttpClientEngine: HttpClientEngineFactory<*>
}