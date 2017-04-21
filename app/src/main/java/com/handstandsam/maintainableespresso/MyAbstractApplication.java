package com.handstandsam.maintainableespresso;

import android.app.Application;

import com.handstandsam.maintainableespresso.di.AppComponent;
import com.handstandsam.maintainableespresso.di.AppModule;
import com.handstandsam.maintainableespresso.di.DaggerAppComponent;
import com.handstandsam.maintainableespresso.di.NetworkModule;
import com.handstandsam.maintainableespresso.di.RepositoryModule;
import com.handstandsam.maintainableespresso.mockaccount.ProduceMockAccount;
import com.handstandsam.maintainableespresso.mockaccount.Stubberator;

public class MyAbstractApplication extends Application {

    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = createAppComponent();
        new Stubberator(this).stubItAll(new ProduceMockAccount());
    }

    protected AppComponent createAppComponent() {
        return DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .networkModule(new NetworkModule("https://api.github.com/"))
                .repositoryModule(new RepositoryModule())
                .build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

}
