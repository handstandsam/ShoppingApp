package com.handstandsam.shoppingapp.di;

import android.app.Application;

import com.handstandsam.shoppingapp.preferences.UserPreferences;
import com.handstandsam.shoppingapp.repository.CheckoutCart;
import com.handstandsam.shoppingapp.repository.SessionManager;

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
    SessionManager sessionManager(CheckoutCart checkoutCart, UserPreferences userPreferences) {
        return new SessionManager(checkoutCart, userPreferences);
    }

    @Singleton
    @Provides
    CheckoutCart checkoutCart() {
        return new CheckoutCart();
    }

    @Singleton
    @Provides
    UserPreferences userPreferences() {
        return new UserPreferences(mApplication);
    }
}