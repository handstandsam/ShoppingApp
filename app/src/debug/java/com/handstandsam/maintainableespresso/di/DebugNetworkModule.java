package com.handstandsam.maintainableespresso.di;

import android.app.Application;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.handstandsam.maintainableespresso.debug.DebugPreferences;
import com.readystatesoftware.chuck.ChuckInterceptor;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

public class DebugNetworkModule extends NetworkModule {

    public DebugNetworkModule(String baseUrl) {
        super(baseUrl);
    }

    @Override
    OkHttpClient.Builder okHttpClientBuilder(Application application) {
        OkHttpClient.Builder builder = super.okHttpClientBuilder(application)
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .addInterceptor(new StethoInterceptor());

        if (new DebugPreferences(application).isChuckEnabled()) {
            builder.addInterceptor(new ChuckInterceptor(application));
        }
        return builder;
    }
}