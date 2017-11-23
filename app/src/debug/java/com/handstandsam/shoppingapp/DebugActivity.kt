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

    private lateinit var mocksCheckbox: CheckBox

    private lateinit var chuckEnabledCheckbox: CheckBox

    private lateinit var applyChangesButton: AppCompatButton

    private lateinit var triggerNotificationButton: AppCompatButton

    private lateinit var usernameEditText: AppCompatEditText

    private lateinit var addShortcutButton: AppCompatButton

    private lateinit var debugPreferences: DebugPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_debug)
        triggerNotificationButton = findViewById(R.id.trigger_notification)
        usernameEditText = findViewById(R.id.username)
        addShortcutButton = findViewById(R.id.add_shortcut)
        mocksCheckbox = findViewById(R.id.mocks_on)
        chuckEnabledCheckbox = findViewById(R.id.chuck)
        applyChangesButton = findViewById(R.id.apply_changes)
        debugPreferences = DebugPreferences(this)

        mocksCheckbox.isChecked = debugPreferences.isMockMode

        mocksCheckbox.setOnCheckedChangeListener { compoundButton, b -> debugPreferences.isMockMode = b }

        chuckEnabledCheckbox.isChecked = debugPreferences.isChuckEnabled

        chuckEnabledCheckbox.setOnCheckedChangeListener { compoundButton, b -> debugPreferences.isChuckEnabled = b }

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
