package com.handstandsam.shoppingapp.debug

import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.support.annotation.DrawableRes
import android.widget.Toast

import com.handstandsam.shoppingapp.R

class HomeScreenShortcutBuilder(context: Context, private val shortcutDisplayName: String,
                                private val deepLinkIntent: Intent) {

    private val applicationContext: Context = context.applicationContext

    private var iconRes: Int = R.mipmap.ic_launcher_round

    fun icon(@DrawableRes iconRes: Int): HomeScreenShortcutBuilder {
        this.iconRes = iconRes
        return this
    }

    fun create() {
        val addShortcutIntent = Intent()
        addShortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, deepLinkIntent)
        addShortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, "My Shortcut")
        addShortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE,
                Intent.ShortcutIconResource.fromContext(applicationContext, iconRes))
        addShortcutIntent.action = "com.android.launcher.action.INSTALL_SHORTCUT"
        applicationContext.sendBroadcast(addShortcutIntent)
    }

    private fun showToast(message: String) {
        Handler(Looper.getMainLooper()).post { Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show() }
    }
}