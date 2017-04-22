package com.handstandsam.maintainableespresso.di;

import android.app.Application;

import com.handstandsam.maintainableespresso.preferences.UserPreferences;
import com.handstandsam.maintainableespresso.repository.CheckoutCart;
import com.handstandsam.maintainableespresso.repository.SessionManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    Application mApplication;

    public AppModule(Application application) {
        mApplication = application;
    }

    @Provides
    Application providesApplication() {
        return mApplication;
    }

    @Singleton
    @Provides
    SessionManager sessionManager(CheckoutCart checkoutCart) {
        return new SessionManager(checkoutCart);
    }

    @Singleton
    @Provides
    CheckoutCart checkoutCart() {
        return new CheckoutCart();
    }

    @Provides
    UserPreferences userPreferences() {
        return new UserPreferences(mApplication);
    }
}