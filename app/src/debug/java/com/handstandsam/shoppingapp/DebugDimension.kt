package com.handstandsam.shoppingapp

import android.app.Application
import android.content.Context
import com.facebook.stetho.Stetho
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.readystatesoftware.chuck.ChuckInterceptor
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor
import timber.log.Timber

fun debugDimensionInterceptors(appContext: Context): List<Interceptor> {
    return listOf(
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY),
        StethoInterceptor(),
        ChuckInterceptor(appContext).showNotification(true)
    )
}

fun Application.debugDimensionOnCreate() {
    Stetho.initializeWithDefaults(this)
    Timber.plant(Timber.DebugTree())
}