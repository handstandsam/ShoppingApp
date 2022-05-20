package com.handstandsam.shoppingapp.multiplatform

import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.engine.okhttp.OkHttp

actual class Platform actual constructor() {
    actual val platform: String = "JVM ${System.getProperty("java.version")}"
    actual val ktorHttpClientEngine: HttpClientEngineFactory<*> = OkHttp
}