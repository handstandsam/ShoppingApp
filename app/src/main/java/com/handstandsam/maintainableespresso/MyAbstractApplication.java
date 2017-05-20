package com.handstandsam.maintainableespresso;

import android.app.Application;

import com.handstandsam.maintainableespresso.di.AppComponent;
import com.handstandsam.maintainableespresso.mockaccount.ProduceMockAccount;
import com.handstandsam.maintainableespresso.mockaccount.Stubberator;

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
