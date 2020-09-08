package com.handstandsam.shoppingapp

import android.app.Application
import android.content.Context
import com.ashokvarma.gander.Gander
import com.ashokvarma.gander.GanderInterceptor
import com.ashokvarma.gander.imdb.GanderIMDB
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor
import timber.log.Timber

fun debugDimensionInterceptors(appContext: Context): List<Interceptor> {
    return listOf(
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY),
        GanderInterceptor(appContext).showNotification(true)
    )
}

fun Application.debugDimensionOnCreate() {
    // For In Memory DB (Data retained in memory lost on app close)
    Gander.setGanderStorage(GanderIMDB.getInstance());
    Timber.plant(Timber.DebugTree())
}