package com.handstandsam.shoppingapp.di;

import android.app.Application;

import com.handstandsam.shoppingapp.network.GitHubService;
import com.handstandsam.shoppingapp.network.ShoppingService;
import com.squareup.moshi.Moshi;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.moshi.MoshiConverterFactory;

@Module
public class NetworkModule {

    public static int LOCALHOST_PORT = 9090;
    public static final String LOCALHOST_ENDPOINT = "http://localhost:" + LOCALHOST_PORT + "/";

    public static boolean USE_LOCAL_SERVER = true;

    public static final int REMOTE_PORT = 8080;
    public static final String REMOTE_EMULATOR_ENDPOINT_HOST = "10.0.2.2";
    public static final String REMOTE_EMULATOR_ENDPOINT = "http://" + REMOTE_EMULATOR_ENDPOINT_HOST + ":" + REMOTE_PORT + "/";

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