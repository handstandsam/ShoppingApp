package com.handstandsam.shoppingapp;

import android.app.Application;

import com.handstandsam.shoppingapp.di.AppComponent;
import com.handstandsam.shoppingapp.mockaccount.ProduceMockAccount;
import com.handstandsam.shoppingapp.mockaccount.Stubberator;

public abstract class MyAbstractApplication extends Application {

    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = createAppComponent();


        //Stubberator (Local)
        new Stubberator(this).stubItAll(new ProduceMockAccount());
//        new Stubberator(this).stubItAll(new VideoGameMockAccount());
//        new Stubberator(this).stubItAll(new AndroidLibsMockAccount());


        //Record
//        WireMockManager wireMockManager = new WireMockManager.Builder(getApplicationContext()).build();
//        wireMockManager.startProxyAndRecord(getApplicationContext(), "http://10.0.2.2:8080");

        //Playback
//        WireMockManager wireMockManager = new WireMockManager.Builder(getApplicationContext(), NetworkModule.LOCALHOST_PORT).build();
//        wireMockManager.startPlayBack();
    }

    protected abstract AppComponent createAppComponent();

    public AppComponent getAppComponent() {
        return appComponent;
    }

}
