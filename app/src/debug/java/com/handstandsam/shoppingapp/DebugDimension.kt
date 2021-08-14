package com.handstandsam.shoppingapp

import android.app.Application
import android.content.Context
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor
import timber.log.Timber

fun debugDimensionInterceptors(appContext: Context): List<Interceptor> {
    return listOf(
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    )
}

fun Application.debugDimensionOnCreate() {
    Timber.plant(Timber.DebugTree())
}