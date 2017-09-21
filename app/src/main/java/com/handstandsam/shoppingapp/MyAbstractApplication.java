package com.handstandsam.shoppingapp;

import android.app.Application;

import com.handstandsam.shoppingapp.di.AppComponent;
import com.handstandsam.shoppingapp.mockaccount.Stubberator;
import com.handstandsam.shoppingapp.mockaccount.VideoGameMockAccount;
import com.handstandsam.shoppingapp.wiremock.WireMockManager;

public abstract class MyAbstractApplication extends Application {

    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = createAppComponent();
        WireMockManager wireMockManager = new WireMockManager.Builder(getApplicationContext()).build();
//        wireMockManager.startProxyAndRecord(getApplicationContext(), "http://10.0.2.2:8080");
//        wireMockManager.startPlayBack();
//        new Stubberator(this).stubItAll(new ProduceMockAccount());
        new Stubberator(this).stubItAll(new VideoGameMockAccount());
//        new Stubberator(this).stubItAll(new AndroidLibsMockAccount());
    }

    protected abstract AppComponent createAppComponent();

    public AppComponent getAppComponent() {
        return appComponent;
    }

}
