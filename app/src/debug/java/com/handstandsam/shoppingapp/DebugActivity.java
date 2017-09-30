package com.handstandsam.shoppingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.handstandsam.shoppingapp.debug.DebugPreferences;
import com.handstandsam.shoppingapp.debug.HomeScreenShortcutBuilder;
import com.handstandsam.shoppingapp.utils.IntentUtils;
import com.jakewharton.processphoenix.ProcessPhoenix;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.handstandsam.shoppingapp.utils.IntentUtils.getIntentForUsername;

public class DebugActivity extends AppCompatActivity {

    @BindView(R.id.mocks_on)
    CheckBox mocksCheckbox;

    @BindView(R.id.chuck)
    CheckBox chuckEnabledCheckbox;

    @BindView(R.id.apply_changes)
    AppCompatButton applyChangesButton;

    @BindView(R.id.trigger_notification)
    AppCompatButton triggerNotificationButton;

    @BindView(R.id.username)
    AppCompatEditText usernameEditText;

    @BindView(R.id.add_shortcut)
    AppCompatButton addShortcutButton;

    private DebugPreferences debugPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debug);
        ButterKnife.bind(this);
        debugPreferences = new DebugPreferences(this);

        mocksCheckbox.setChecked(debugPreferences.isMockMode());

        mocksCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                debugPreferences.setMockMode(b);
            }
        });

        chuckEnabledCheckbox.setChecked(debugPreferences.isChuckEnabled());

        chuckEnabledCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                debugPreferences.setChuckEnabled(b);
            }
        });

        applyChangesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProcessPhoenix.triggerRebirth(DebugActivity.this);
            }
        });

        addShortcutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = usernameEditText.getText().toString();
                Intent intent = getIntentForUsername(DebugActivity.this, username);
                new HomeScreenShortcutBuilder(DebugActivity.this, username, intent).create();
            }
        });

        triggerNotificationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = usernameEditText.getText().toString();
                IntentUtils.triggerPRNotification(DebugActivity.this, username);
            }
        });
    }
}
