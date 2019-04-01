package com.handstandsam.shoppingapp

fun OkHttpClient.Builder.debugDimensionAddInterceptors(appContext: Context): OkHttpClient.Builder {
    return this
}

fun Application.debugDimensionOnCreate() {
    // No Logging
}