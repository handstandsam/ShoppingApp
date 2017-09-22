package com.handstandsam.shoppingapp;

import android.app.Application;

import com.handstandsam.shoppingapp.di.AppComponent;
import com.handstandsam.shoppingapp.di.NetworkModule;
import com.handstandsam.shoppingapp.mockaccount.VideoGameMockAccount;

public abstract class MyAbstractApplication extends Application {

    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        String endpoint;
        endpoint = NetworkModule.LOCALHOST_ENDPOINT;
//        endpoint = NetworkModule.LAPTOP_FROM_EMULATOR_ENDPOINT;
//        endpoint = NetworkModule.S3_ENDPOINT;
        appComponent = createAppComponent(endpoint);

        NetworkConfig networkConfig = new NetworkConfig(this);
//        networkConfig.startNormally();
        networkConfig.stubLocalWireMock(new VideoGameMockAccount());
//        networkConfig.stubRemoteWireMock(new ProduceMockAccount());
//        networkConfig.recordMappingsAndProxy(endpoint);
//        networkConfig.playbackRecordedMappings();

    }

    protected abstract AppComponent createAppComponent(String endpoint);

    public AppComponent getAppComponent() {
        return appComponent;
    }

}
