package com.handstandsam.maintainableespresso;

import android.app.Application;

import com.handstandsam.maintainableespresso.di.AppComponent;
import com.handstandsam.maintainableespresso.di.AppModule;
import com.handstandsam.maintainableespresso.di.DaggerAppComponent;
import com.handstandsam.maintainableespresso.di.NetworkModule;

public class MyAbstractApplication extends Application {

    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = createAppComponent();
    }

    protected AppComponent createAppComponent() {
        return DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .networkModule(new NetworkModule("https://api.github.com/"))
                .build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

}
