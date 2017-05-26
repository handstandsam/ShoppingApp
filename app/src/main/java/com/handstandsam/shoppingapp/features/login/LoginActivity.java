package com.handstandsam.shoppingapp.features.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatEditText;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.handstandsam.shoppingapp.MyAbstractApplication;
import com.handstandsam.shoppingapp.R;
import com.handstandsam.shoppingapp.di.AppComponent;
import com.handstandsam.shoppingapp.features.home.HomeActivity;
import com.handstandsam.shoppingapp.network.GitHubService;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.disposables.Disposable;

public class LoginActivity extends AppCompatActivity {

    private static final String EXTRA_USERNAME = "username";

    @Inject
    GitHubService gitHubService;

    @BindView(R.id.remember_me)
    AppCompatCheckBox rememberMeCheckbox;

    @BindView(R.id.username)
    AppCompatEditText usernameEditText;

    @BindView(R.id.password)
    AppCompatEditText passwordEditText;

    Disposable disposable;
    private MyLoginView loginView;
    private LoginPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().setTitle("Log in to Shopping App");
        ButterKnife.bind(this);
        ((MyAbstractApplication) getApplication()).getAppComponent().inject(this);

        loginView = new MyLoginView();
        presenter = new LoginPresenter(loginView);

        usernameEditText.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    submit();
                    return true;
                }
                return false;
            }
        });
    }

    @OnClick(R.id.submit)
    public void submit() {
        presenter.loginClicked();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }

    public interface LoginView {
        AppComponent getAppComponent();

        void startHomeActivity();

        String getUsername();

        boolean isRememberMeChecked();

        void setUsername(String username);

        void setRememberMe(boolean rememberMe);

        void kickToHomeScreen();

        String getPassword();

        void showToast(@StringRes int stringResourceId);
    }


    public class MyLoginView implements LoginView {

        @Override
        public AppComponent getAppComponent() {
            return ((MyAbstractApplication) getApplication()).getAppComponent();
        }

        @Override
        public void startHomeActivity() {
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            LoginActivity.this.startActivity(intent);
        }

        @Override
        public String getUsername() {
            return usernameEditText.getText().toString();
        }

        @Override
        public boolean isRememberMeChecked() {
            return rememberMeCheckbox.isChecked();
        }

        @Override
        public void setUsername(String username) {
            usernameEditText.setText(username);
        }

        @Override
        public void setRememberMe(boolean value) {
            rememberMeCheckbox.setChecked(value);
        }

        @Override
        public void kickToHomeScreen() {
            LoginActivity.this.startActivity(new Intent(LoginActivity.this, HomeActivity.class));
            finish();
        }

        @Override
        public String getPassword() {
            return passwordEditText.getText().toString();
        }

        @Override
        public void showToast(@StringRes int stringResourceId) {
            Toast.makeText(getApplicationContext(), stringResourceId, Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.onResume(getIntent());
    }
}
