package com.handstandsam.maintainableespresso.di;

import android.app.Application;

import com.handstandsam.maintainableespresso.repository.SessionManager;

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

    @Provides
    SessionManager sessionManager() {
        return new SessionManager();
    }
}