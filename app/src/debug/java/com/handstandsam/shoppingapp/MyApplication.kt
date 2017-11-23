package com.handstandsam.shoppingapp

import android.os.Handler
import android.os.Looper
import android.os.StrictMode
import android.widget.Toast
import com.handstandsam.shoppingapp.debug.DebugDispatcher
import com.handstandsam.shoppingapp.debug.DebugPreferences
import com.handstandsam.shoppingapp.di.*
import okhttp3.mockwebserver.MockWebServer
import timber.log.Timber
import java.io.IOException

class MyApplication : MyAbstractApplication() {

    override fun onCreate() {
        Timber.plant(Timber.DebugTree())
        super.onCreate()
    }

    private fun startMockWebServer() {
        if (server != null) {
            Timber.w("Server already started on port: " + server?.port)
            return
        }

        try {
            //Have to do this to start the server synchronously on the main thread (not recommended, but this is a debug feature)
            val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
            StrictMode.setThreadPolicy(policy)
            server = MockWebServer()
            server!!.start()
            Timber.w("Server started on port: " + server?.port)
            server!!.setDispatcher(DebugDispatcher(this))
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    override fun createAppComponent(pEndpoint: String): AppComponent {
        var endpoint = pEndpoint
        if (DebugPreferences(this).isMockMode) {
            startMockWebServer()
            endpoint = server!!.url("/").toString()
            Handler(Looper.getMainLooper()).post { Toast.makeText(this, "Using Mock Server", Toast.LENGTH_SHORT).show() }
        }

        return DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .networkModule(DebugNetworkModule(endpoint))
                .repositoryModule(RepositoryModule(this))
                .build()
    }

    companion object {

        var server: MockWebServer? = null
    }

}
