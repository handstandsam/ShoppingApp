package com.handstandsam.shoppingapp.di

import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import okhttp3.OkHttpClient

object OkHttpKtorClientBuilder {

    fun createKtorClient(okHttpClient: OkHttpClient = OkHttpClient.Builder().build()): HttpClient {
        return HttpClient(OkHttp) {
            engine {
                preconfigured = okHttpClient
            }
        }
    }
}