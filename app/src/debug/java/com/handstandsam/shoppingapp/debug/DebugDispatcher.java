package com.handstandsam.shoppingapp.debug;


import android.content.Context;
import android.net.Uri;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import okhttp3.mockwebserver.Dispatcher;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.RecordedRequest;

public class DebugDispatcher extends Dispatcher {

    Context context;

    public DebugDispatcher(Context context) {
        this.context = context;
    }

    @Override
    public MockResponse dispatch(RecordedRequest request) throws InterruptedException {
        Uri uri = Uri.parse(request.getPath());
        String username = uri.getLastPathSegment();

        String assetResponseJson = getAsset("user_" + username.toLowerCase() + ".json");
        if (assetResponseJson != null && !assetResponseJson.isEmpty()) {
            return new MockResponse().setBody(assetResponseJson).setResponseCode(200);
        } else {
            return new MockResponse().setResponseCode(404);
        }
    }

    private String getAsset(String filename) {
        StringBuilder total = new StringBuilder();
        try {
            InputStream inputStream = context.getAssets().open(filename);
            BufferedReader r = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = r.readLine()) != null) {
                total.append(line).append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return total.toString();
    }
}
