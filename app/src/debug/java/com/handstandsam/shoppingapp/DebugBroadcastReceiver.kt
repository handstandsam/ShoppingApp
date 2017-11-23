package com.handstandsam.shoppingapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.handstandsam.shoppingapp.utils.IntentUtils
import timber.log.Timber


class DebugBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        handleIntent(context, intent)
    }

    private fun handleIntent(context: Context, intent: Intent) {
        val intentAction = intent.action

        if (intentAction == null || PR_NOTIFICATION != intentAction) {
            return
        }
        Timber.d("processing " + PR_NOTIFICATION)

        val extras = intent.extras ?: return

        val username = extras.getString("username")
        Timber.d("username " + username!!)

        IntentUtils.triggerPRNotification(context, username)
    }

    companion object {

        private val PR_NOTIFICATION = "PR_NOTIFICATION"
    }
}
