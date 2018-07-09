package com.handstandsam.shoppingapp

import timber.log.Timber

class MyApplication : MyAbstractApplication() {

    override fun onCreate() {
        Timber.plant(Timber.DebugTree())
        super.onCreate()
    }
}
