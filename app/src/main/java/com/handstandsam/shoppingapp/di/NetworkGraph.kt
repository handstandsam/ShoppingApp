package com.handstandsam.shoppingapp.di

import android.content.Context
import com.handstandsam.shoppingapp.network.NetworkManager
import com.handstandsam.shoppingapp.network.ShoppingService
import com.handstandsam.shoppingapp.repository.*
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

interface NetworkGraph {
    val categoryRepo: CategoryRepo
    val itemRepo: ItemRepo
    val userRepo: UserRepo
    val retrofitBuilder: Retrofit.Builder
    val okHttpClientBuilder: OkHttpClient.Builder
    val retrofit: Retrofit
    val shoppingService: ShoppingService
    val networkConfig: NetworkConfig
    val networkManager: NetworkManager
}

open class NetworkGraphImpl(val appContext: Context) :
    NetworkGraph {

    override val networkConfig: NetworkConfig = NetworkConfigs.LOCALHOST

    override val networkManager: NetworkManager by lazy {
        NetworkManager(appContext, networkConfig)
    }

    override val retrofitBuilder: Retrofit.Builder by lazy {
        Retrofit.Builder()
            .baseUrl(networkConfig.fullUrl)
            .addConverterFactory(MoshiConverterFactory.create(Moshi.Builder().build()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClientBuilder.build())
    }

    override val okHttpClientBuilder by lazy {
        OkHttpClient.Builder()
    }

    override val retrofit: Retrofit by lazy { retrofitBuilder.build() }

    override val shoppingService: ShoppingService by lazy { retrofit.create(ShoppingService::class.java) }

    override val categoryRepo by lazy { NetworkCategoryRepo(shoppingService) }

    override val itemRepo by lazy { NetworkItemRepo(shoppingService) }

    override val userRepo by lazy { NetworkUserRepo(shoppingService) }

}

object NetworkConstants {
    var LOCALHOST_PORT = 8080
    val LOCALHOST_ENDPOINT = "http://localhost:" + LOCALHOST_PORT
    val S3_ENDPOINT = "https://shopping-app.s3.amazonaws.com"

    var USE_LOCAL_SERVER = true

    val REMOTE_PORT = 8080
    val REMOTE_EMULATOR_ENDPOINT_HOST = "10.0.2.2"
    var LAPTOP_FROM_EMULATOR_ENDPOINT = "http://$REMOTE_EMULATOR_ENDPOINT_HOST:$REMOTE_PORT"
}

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

object NetworkConfigs {
    val LOCALHOST: NetworkConfig = NetworkConfig()
    val LAPTOP: NetworkConfig = NetworkConfig(baseUrl = "http://10.0.2.2:8080")
    val S_3_LIVE_ENDPOINT: NetworkConfig =
        NetworkConfig(baseUrl = "https://shopping-app.s3.amazonaws.com")
    val RECORD_S_3_LIVE_ENDPOINT: NetworkConfig =
        NetworkConfig(recordProxyEndpoint = "https://shopping-app.s3.amazonaws.com")
    val PLAYBACK_FILE_BASED_MAPPINGS: NetworkConfig =
        NetworkConfig(loadAndPlaybackFileBasedMappings = true)
}