package com.handstandsam.shoppingapp.di

import android.app.Application

import com.facebook.stetho.okhttp3.StethoInterceptor
import com.handstandsam.shoppingapp.debug.DebugPreferences
import com.readystatesoftware.chuck.ChuckInterceptor

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

class DebugNetworkModule(baseUrl: String) : NetworkModule(baseUrl) {

    override fun okHttpClientBuilder(application: Application): OkHttpClient.Builder {
        val builder = super.okHttpClientBuilder(application)
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .addInterceptor(StethoInterceptor())

        if (DebugPreferences(application).isChuckEnabled) {
            builder.addInterceptor(ChuckInterceptor(application))
        }
        return builder
    }
}