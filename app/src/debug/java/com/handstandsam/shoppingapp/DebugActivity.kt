package com.handstandsam.shoppingapp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.AppCompatButton
import android.support.v7.widget.AppCompatEditText
import android.widget.CheckBox
import com.handstandsam.shoppingapp.debug.DebugPreferences
import com.handstandsam.shoppingapp.debug.HomeScreenShortcutBuilder
import com.handstandsam.shoppingapp.utils.IntentUtils
import com.handstandsam.shoppingapp.utils.IntentUtils.getIntentForUsername
import com.jakewharton.processphoenix.ProcessPhoenix

class DebugActivity : AppCompatActivity() {

    internal lateinit var mocksCheckbox: CheckBox

    internal lateinit var chuckEnabledCheckbox: CheckBox

    internal lateinit var applyChangesButton: AppCompatButton

    internal lateinit var triggerNotificationButton: AppCompatButton

    internal lateinit var usernameEditText: AppCompatEditText

    internal lateinit var addShortcutButton: AppCompatButton

    internal var debugPreferences: DebugPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_debug)
        triggerNotificationButton = findViewById<AppCompatButton>(R.id.trigger_notification)
        usernameEditText = findViewById<AppCompatEditText>(R.id.username)
        addShortcutButton = findViewById<AppCompatButton>(R.id.add_shortcut)
        mocksCheckbox = findViewById<CheckBox>(R.id.mocks_on)
        chuckEnabledCheckbox = findViewById<CheckBox>(R.id.chuck)
        applyChangesButton = findViewById<AppCompatButton>(R.id.apply_changes)
        debugPreferences = DebugPreferences(this)

        mocksCheckbox.isChecked = debugPreferences!!.isMockMode

        mocksCheckbox.setOnCheckedChangeListener { compoundButton, b -> debugPreferences!!.isMockMode = b }

        chuckEnabledCheckbox.isChecked = debugPreferences!!.isChuckEnabled

        chuckEnabledCheckbox.setOnCheckedChangeListener { compoundButton, b -> debugPreferences!!.isChuckEnabled = b }

        applyChangesButton.setOnClickListener { ProcessPhoenix.triggerRebirth(this@DebugActivity) }

        addShortcutButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val intent = getIntentForUsername(this@DebugActivity, username)
            HomeScreenShortcutBuilder(this@DebugActivity, username, intent).create()
        }

        triggerNotificationButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            IntentUtils.triggerPRNotification(this@DebugActivity, username)
        }
    }
}
