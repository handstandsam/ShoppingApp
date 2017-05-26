package com.handstandsam.shoppingapp.debug;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.handstandsam.shoppingapp.R;

public class HomeScreenShortcutBuilder {

    private final Context applicationContext;

    private final String shortcutDisplayName;

    private Intent deepLinkIntent;

    private int iconRes;

    public HomeScreenShortcutBuilder(@NonNull Context context, String shortcutDisplayName,
                                     @NonNull Intent deepLinkIntent) {
        this.applicationContext = context.getApplicationContext();
        this.shortcutDisplayName = shortcutDisplayName;
        this.deepLinkIntent = deepLinkIntent;
        iconRes = R.mipmap.ic_launcher_round;
    }

    public HomeScreenShortcutBuilder icon(@DrawableRes int iconRes) {
        this.iconRes = iconRes;
        return this;
    }

    public void create() {
        Intent addShortcutIntent = new Intent();
        addShortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, deepLinkIntent);
        addShortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, "My Shortcut");
        addShortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE,
                Intent.ShortcutIconResource.fromContext(applicationContext, iconRes));
        addShortcutIntent.setAction("com.android.launcher.action.INSTALL_SHORTCUT");
        applicationContext.sendBroadcast(addShortcutIntent);
    }

    private void showToast(final String message) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show();
            }
        });
    }
}