package com.handstandsam.shoppingapp.di

import android.content.Context
import com.handstandsam.shoppingapp.NetworkConfigs
import com.handstandsam.shoppingapp.debugDimensionAddInterceptors
import com.handstandsam.shoppingapp.models.NetworkConfig
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
}

open class NetworkGraphImpl(private val appContext: Context) :
    NetworkGraph {

    open val networkConfig: NetworkConfig = NetworkConfigs.LOCALHOST

    private val retrofitBuilder: Retrofit.Builder by lazy {
        Retrofit.Builder()
            .baseUrl(networkConfig.fullUrl)
            .addConverterFactory(MoshiConverterFactory.create(Moshi.Builder().build()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClientBuilder.build())
    }

    protected open val okHttpClientBuilder by lazy {
        OkHttpClient.Builder().debugDimensionAddInterceptors(appContext)
    }

    private val retrofit: Retrofit by lazy { retrofitBuilder.build() }

    private val shoppingService: ShoppingService by lazy { retrofit.create(ShoppingService::class.java) }

    override val categoryRepo: CategoryRepo by lazy { NetworkCategoryRepo(shoppingService) }

    override val itemRepo: ItemRepo by lazy { NetworkItemRepo(shoppingService) }

    override val userRepo: UserRepo by lazy { NetworkUserRepo(shoppingService) }

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