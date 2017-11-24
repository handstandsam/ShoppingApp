package com.handstandsam.shoppingapp.di

import android.app.Application

import com.handstandsam.shoppingapp.network.ShoppingService
import com.squareup.moshi.Moshi

import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
open class NetworkModule(private var baseUrl: String) {

    init {
        if (!baseUrl.endsWith("/")) {
            baseUrl += "/"
        }
    }

    @Provides
    internal fun retrofitBuilder(okHttpClientBuilder: OkHttpClient.Builder): Retrofit.Builder {
        return Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(MoshiConverterFactory.create(Moshi.Builder().build()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClientBuilder.build())
    }

    @Provides
    internal open fun okHttpClientBuilder(application: Application): OkHttpClient.Builder {
        return OkHttpClient.Builder()
    }

    @Provides
    internal fun retrofit(okHttpClientBuilder: OkHttpClient.Builder): Retrofit {
        return retrofitBuilder(okHttpClientBuilder).build()
    }

    @Provides
    internal fun shoppingService(retrofit: Retrofit): ShoppingService {
        return retrofit.create(ShoppingService::class.java)
    }

    companion object {

        var LOCALHOST_PORT = 8080
        val LOCALHOST_ENDPOINT = "http://localhost:" + LOCALHOST_PORT
        val S3_ENDPOINT = "https://shopping-app.s3.amazonaws.com"

        var USE_LOCAL_SERVER = true

        val REMOTE_PORT = 8080
        val REMOTE_EMULATOR_ENDPOINT_HOST = "10.0.2.2"
        var LAPTOP_FROM_EMULATOR_ENDPOINT = "http://$REMOTE_EMULATOR_ENDPOINT_HOST:$REMOTE_PORT"
    }
}