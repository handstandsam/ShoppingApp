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
        localWireMock();
//        record();
//        playback();
//        connectToLaptop();
    }


    protected abstract AppComponent createAppComponent(String endpoint);

    public AppComponent getAppComponent() {
        return appComponent;
    }

    public void startNormally() {
        appComponent = createAppComponent(NetworkModule.S3_ENDPOINT);
        NetworkConfig networkConfig = new NetworkConfig(this);
        networkConfig.startNormally();
    }

    public void record() {
        appComponent = createAppComponent("http://localhost:8080");
        NetworkConfig networkConfig = new NetworkConfig(this);
        networkConfig.recordMappingsAndProxy(NetworkModule.S3_ENDPOINT);
    }

    public void playback() {
        appComponent = createAppComponent("http://localhost:8080");
        NetworkConfig networkConfig = new NetworkConfig(this);
        networkConfig.playbackRecordedMappings();
    }

    public void connectToLaptop() {
        appComponent = createAppComponent("http://10.0.2.2:8080");
        NetworkConfig networkConfig = new NetworkConfig(this);
        networkConfig.startNormally();
    }

    public void localWireMock() {
        String endpoint;

        endpoint = "http://localhost:8080"; //LOCALHOST_ENDPOINT
        appComponent = createAppComponent(endpoint);

        NetworkConfig networkConfig = new NetworkConfig(this);
        networkConfig.stubLocalWireMock(new VideoGameMockAccount());
//        networkConfig.stubRemoteWireMock(new AndroidLibsMockAccount());
    }

}
