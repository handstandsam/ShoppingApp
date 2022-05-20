package com.handstandsam.shoppingapp.multiplatform

import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.engine.darwin.Darwin
import platform.UIKit.UIDevice

actual class Platform actual constructor() {
    actual val platform: String =
        UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
    actual val ktorHttpClientEngine: HttpClientEngineFactory<*> = Darwin
}