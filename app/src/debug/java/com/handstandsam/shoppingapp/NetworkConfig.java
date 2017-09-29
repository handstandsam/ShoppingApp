package com.handstandsam.shoppingapp;

import android.content.Context;

import com.handstandsam.shoppingapp.di.NetworkModule;
import com.handstandsam.shoppingapp.mockaccount.MockAccount;
import com.handstandsam.shoppingapp.mockaccount.Stubberator;
import com.handstandsam.shoppingapp.wiremock.WireMockManager;


class NetworkConfig {
    Context applicationContext;

    public NetworkConfig(Context applicationContext) {
        this.applicationContext = applicationContext;
    }

    public void startNormally() {
    }

    public void stubLocalWireMock(MockAccount mockAccount) {
        NetworkModule.USE_LOCAL_SERVER = true;
        new Stubberator(applicationContext).stubItAll(mockAccount);
    }

    public void stubRemoteWireMock(MockAccount mockAccount) {
        NetworkModule.USE_LOCAL_SERVER = false;
        new Stubberator(applicationContext).stubItAll(mockAccount);
    }


    public void recordMappingsAndProxy(String remoteBaseUrl) {
//                Record
        WireMockManager wireMockManager = new WireMockManager.Builder(applicationContext, NetworkModule.LOCALHOST_PORT).build();
        wireMockManager.startProxyAndRecord(remoteBaseUrl);
    }


    public void playbackRecordedMappings() {
//                Playback
        WireMockManager wireMockManager = new WireMockManager.Builder(applicationContext, NetworkModule.LOCALHOST_PORT).build();
        wireMockManager.startPlayBack();
    }
}
