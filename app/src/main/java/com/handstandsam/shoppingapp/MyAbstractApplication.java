package com.handstandsam.shoppingapp;

import android.app.Application;

import com.handstandsam.shoppingapp.di.AppComponent;

public abstract class MyAbstractApplication extends Application {

    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = createAppComponent();
    }

    protected abstract AppComponent createAppComponent();

    public AppComponent getAppComponent() {
        return appComponent;
    }

}
