package com.handstandsam.shoppingapp.di

import android.content.Context
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.handstandsam.shoppingapp.NetworkConfigs
import com.handstandsam.shoppingapp.debug.DebugPreferences
import com.handstandsam.shoppingapp.models.NetworkConfig
import com.readystatesoftware.chuck.ChuckInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

class DebugNetworkGraph(
    appContext: Context
) : NetworkGraphImpl(appContext) {

    override val networkConfig: NetworkConfig = NetworkConfigs.LOCALHOST

    override val okHttpClientBuilder: OkHttpClient.Builder by lazy {
        val builder = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .addInterceptor(StethoInterceptor())

        if (DebugPreferences(appContext).isChuckEnabled) {
            builder.addInterceptor(ChuckInterceptor(appContext))
        }

        builder
    }
}