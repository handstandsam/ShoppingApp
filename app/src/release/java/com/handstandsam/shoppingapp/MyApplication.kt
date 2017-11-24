package com.handstandsam.shoppingapp

import com.handstandsam.shoppingapp.di.*

class MyApplication : MyAbstractApplication() {

    override fun createAppComponent(endpoint: String): AppComponent {
        return DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .networkModule(NetworkModule(NetworkModule.LOCALHOST_ENDPOINT))
                .repositoryModule(RepositoryModule(this))
                .build()
    }
}
