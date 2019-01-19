package com.handstandsam.shoppingapp.di

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

open class BaseNetworkGraph(
    networkConfig: NetworkConfig
) : NetworkGraph {

    private val okHttpClientBuilder = OkHttpClient.Builder()

    private val moshi = Moshi.Builder().build()

    private val moshiConverterFactory = MoshiConverterFactory.create(moshi)

    private val rxJava2CallAdapterFactory = RxJava2CallAdapterFactory.create()

    private val retrofitBuilder: Retrofit.Builder =
        Retrofit.Builder()
            .baseUrl(networkConfig.fullUrl)
            .addConverterFactory(moshiConverterFactory)
            .addCallAdapterFactory(rxJava2CallAdapterFactory)
            .client(okHttpClientBuilder.build())

    private val retrofit: Retrofit = retrofitBuilder.build()

    private val shoppingService: ShoppingService = retrofit.create(ShoppingService::class.java)

    override val categoryRepo: CategoryRepo = NetworkCategoryRepo(shoppingService)

    override val itemRepo: ItemRepo = NetworkItemRepo(shoppingService)

    override val userRepo: UserRepo = NetworkUserRepo(shoppingService)

}