package com.handstandsam.maintainableespresso;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.handstandsam.maintainableespresso.features.checkout.CheckoutActivity;
import com.handstandsam.maintainableespresso.features.login.LoginActivity;
import com.handstandsam.maintainableespresso.repository.SessionManager;

import javax.inject.Inject;

public class LoggedInActivity extends AppCompatActivity {

    @Inject
    protected SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((MyAbstractApplication) getApplication()).getAppComponent().inject(this);
        if (!sessionManager.isLoggedIn()) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }
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
            case R.id.view_cart:
                startActivity(new Intent(this, CheckoutActivity.class));
                return true;
            case android.R.id.home: {
                onBackPressed();
                return true;
            }
            default:
                return super.onContextItemSelected(item);
        }
    }
}
