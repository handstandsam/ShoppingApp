package com.handstandsam.maintainableespresso;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.handstandsam.maintainableespresso.utils.IntentUtils;

import timber.log.Timber;

public class DebugBroadcastReceiver extends BroadcastReceiver {

    private static final String PR_NOTIFICATION = "PR_NOTIFICATION";


    public DebugBroadcastReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        handleIntent(context, intent);
    }

    void handleIntent(Context context, @NonNull Intent intent) {
        String intentAction = intent.getAction();

        if (intentAction == null || !PR_NOTIFICATION.equals(intentAction)) {
            return;
        }
        Timber.d("processing " + PR_NOTIFICATION);

        Bundle extras = intent.getExtras();
        if (extras == null) {
            return;
        }

        final String username = extras.getString("username");
        Timber.d("username " + username);

        IntentUtils.triggerPRNotification(context, username);
    }
}
