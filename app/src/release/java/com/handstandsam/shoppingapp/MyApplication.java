package com.handstandsam.shoppingapp;

import com.handstandsam.shoppingapp.di.AppComponent;
import com.handstandsam.shoppingapp.di.AppModule;
import com.handstandsam.shoppingapp.di.DaggerAppComponent;
import com.handstandsam.shoppingapp.di.NetworkModule;
import com.handstandsam.shoppingapp.di.RepositoryModule;

public class MyApplication extends MyAbstractApplication {

    @Override
    protected AppComponent createAppComponent() {
        return DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .networkModule(new NetworkModule(NetworkModule.LOCALHOST_ENDPOINT))
                .repositoryModule(new RepositoryModule(this))
                .build();
    }

}
