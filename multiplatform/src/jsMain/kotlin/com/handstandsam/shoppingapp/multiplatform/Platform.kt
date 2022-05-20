package com.handstandsam.shoppingapp.multiplatform

import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.engine.js.Js

actual class Platform actual constructor() {
    actual val platform: String = "This is JS"
    actual val ktorHttpClientEngine: HttpClientEngineFactory<*> = Js
}