package com.handstandsam.shoppingapp;

import android.app.Application;

import com.handstandsam.shoppingapp.di.AppComponent;
import com.handstandsam.shoppingapp.mockaccount.ProduceMockAccount;
import com.handstandsam.shoppingapp.mockaccount.Stubberator;

import timber.log.Timber;

public abstract class MyAbstractApplication extends Application {

    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = createAppComponent();
        new Stubberator(this).stubItAll(new ProduceMockAccount());
//        new Stubberator(this).stubItAll(new VideoGameMockAccount());
    }

    protected abstract AppComponent createAppComponent();

    public AppComponent getAppComponent() {
        return appComponent;
    }

}
