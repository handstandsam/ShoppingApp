package com.handstandsam.maintainableespresso.di;

import android.app.Application;

import com.handstandsam.maintainableespresso.network.GitHubService;
import com.handstandsam.maintainableespresso.network.ShoppingService;
import com.squareup.moshi.Moshi;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.moshi.MoshiConverterFactory;

@Module
public class NetworkModule {

    public static final String IMAGE_BASE_URL = "https://s3.amazonaws.com/maintainable-espresso/images/";

    protected String baseUrl;

    public NetworkModule(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @Provides
    Retrofit.Builder retrofitBuilder(OkHttpClient.Builder okHttpClientBuilder) {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(MoshiConverterFactory.create(new Moshi.Builder().build()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClientBuilder.build());
        return builder;
    }

    @Provides
    OkHttpClient.Builder okHttpClientBuilder(Application application) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        return builder;
    }

    @Provides
    Retrofit retrofit(OkHttpClient.Builder okHttpClientBuilder) {
        return retrofitBuilder(okHttpClientBuilder).build();
    }

    @Provides
    GitHubService gitHubService(Retrofit retrofit) {
        return retrofit.create(GitHubService.class);
    }

    @Provides
    ShoppingService shoppingService(Retrofit retrofit) {
        return retrofit.create(ShoppingService.class);
    }
}