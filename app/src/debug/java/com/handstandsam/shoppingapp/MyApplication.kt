package com.handstandsam.shoppingapp

import com.handstandsam.shoppingapp.di.*
import timber.log.Timber

class MyApplication : MyAbstractApplication() {

    override fun onCreate() {
        Timber.plant(Timber.DebugTree())
        super.onCreate()
    }

    override fun createAppComponent(pEndpoint: String): AppComponent {
        return DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .networkModule(DebugNetworkModule(NetworkModule.LOCALHOST_ENDPOINT))
                .repositoryModule(RepositoryModule(this))
                .build()
    }
}
