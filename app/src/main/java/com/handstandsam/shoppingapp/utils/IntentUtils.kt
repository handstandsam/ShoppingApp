package com.handstandsam.shoppingapp.utils

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.handstandsam.shoppingapp.R
import com.handstandsam.shoppingapp.features.login.LoginActivity

object IntentUtils {

    @JvmStatic
    fun getIntentForUsername(context: Context, username: String): Intent {
        val intent = Intent(context, LoginActivity::class.java)
        val bundle = Bundle()
        bundle.putString("username", username)
        intent.putExtras(bundle)
        return intent
    }

    @JvmStatic
    fun triggerPRNotification(context: Context, username: String) {
        val id = System.currentTimeMillis().toInt()
        val pIntent = PendingIntent.getActivity(
            context,
            id,
            getIntentForUsername(context, username),
            PendingIntent.FLAG_ONE_SHOT
        )

        val n = Notification.Builder(context)
            .setContentTitle("New Order from " + username)
            .setContentText("Added Debug Features to App.")
            .setSmallIcon(R.drawable.ic_shopping_cart_white_24px)
            .setContentIntent(pIntent)
            .setAutoCancel(true).build()

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.notify(id, n)
    }

}
