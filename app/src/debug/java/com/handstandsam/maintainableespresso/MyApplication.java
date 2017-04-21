package com.handstandsam.maintainableespresso;

import android.os.Handler;
import android.os.Looper;
import android.os.StrictMode;
import android.widget.Toast;

import com.handstandsam.maintainableespresso.debug.DebugDispatcher;
import com.handstandsam.maintainableespresso.debug.DebugPreferences;
import com.handstandsam.maintainableespresso.di.AppComponent;
import com.handstandsam.maintainableespresso.di.AppModule;
import com.handstandsam.maintainableespresso.di.DaggerAppComponent;
import com.handstandsam.maintainableespresso.di.DebugNetworkModule;
import com.handstandsam.maintainableespresso.di.RepositoryModule;

import java.io.IOException;

import okhttp3.mockwebserver.MockWebServer;
import timber.log.Timber;

public class MyApplication extends MyAbstractApplication {

    public static MockWebServer server;


    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new Timber.DebugTree());
    }

    private void startMockWebServer() {
        if (server != null) {
            Timber.w("Server already started on port: " + server.getPort());
            return;
        }

        try {
            //Have to do this to start the server synchronously on the main thread (not recommended, but this is a debug feature)
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            server = new MockWebServer();
            server.start();
            Timber.w("Server started on port: " + server.getPort());
            server.setDispatcher(new DebugDispatcher(this));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected AppComponent createAppComponent() {
        String endpoint = "https://api.github.com/";
        if (new DebugPreferences(this).isMockMode()) {
            startMockWebServer();
            endpoint = server.url("/").toString();
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(MyApplication.this, "Using Mock Server", Toast.LENGTH_SHORT).show();
                }
            });
        }

        return DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .networkModule(new DebugNetworkModule(endpoint))
                .repositoryModule(new RepositoryModule())
                .build();
    }

}
