package com.handstandsam.maintainableespresso;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.handstandsam.maintainableespresso.features.login.LoginActivity;
import com.handstandsam.maintainableespresso.repository.SessionManager;

import javax.inject.Inject;

public class LoggedInActivity extends AppCompatActivity {

    @Inject
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((MyAbstractApplication) getApplication()).getAppComponent().inject(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.logged_in_menu, menu);
        return (super.onCreateOptionsMenu(menu));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                sessionManager.logout();
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
}
