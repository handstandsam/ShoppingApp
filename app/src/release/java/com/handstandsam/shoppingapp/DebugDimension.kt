package com.handstandsam.shoppingapp

fun OkHttpClient.Builder.debugDimensionInterceptors(appContext: Context): OkHttpClient.Builder {
    return this
}

fun Application.debugDimensionOnCreate() {
    // No Logging
}