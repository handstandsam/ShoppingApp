package com.handstandsam.shoppingapp.utils

import android.content.Context
import android.content.Intent
import android.os.Bundle
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

}
