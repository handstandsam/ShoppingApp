package com.handstandsam.shoppingapp.di

import android.app.Application

import com.handstandsam.shoppingapp.preferences.UserPreferences
import com.handstandsam.shoppingapp.repository.CheckoutCart
import com.handstandsam.shoppingapp.repository.SessionManager

import javax.inject.Singleton

import dagger.Module
import dagger.Provides

@Module
class AppModule(internal var mApplication: Application) {

    @Provides
    internal fun providesApplication(): Application {
        return mApplication
    }

    @Singleton
    @Provides
    internal fun sessionManager(checkoutCart: CheckoutCart, userPreferences: UserPreferences): SessionManager {
        return SessionManager(checkoutCart, userPreferences)
    }

    @Singleton
    @Provides
    internal fun checkoutCart(): CheckoutCart {
        return CheckoutCart()
    }

    @Singleton
    @Provides
    internal fun userPreferences(): UserPreferences {
        return UserPreferences(mApplication)
    }
}