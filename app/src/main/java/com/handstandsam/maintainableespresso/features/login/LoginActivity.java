package com.handstandsam.maintainableespresso.features.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatEditText;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.handstandsam.maintainableespresso.MyAbstractApplication;
import com.handstandsam.maintainableespresso.R;
import com.handstandsam.maintainableespresso.di.AppComponent;
import com.handstandsam.maintainableespresso.features.home.HomeActivity;
import com.handstandsam.maintainableespresso.network.GitHubService;
import com.handstandsam.maintainableespresso.network.model.GitHubUser;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class LoginActivity extends AppCompatActivity {

    private static final String EXTRA_USERNAME = "username";

    @Inject
    GitHubService gitHubService;

    @BindView(R.id.remember_me)
    AppCompatCheckBox rememberMeCheckbox;

    @BindView(R.id.username)
    AppCompatEditText usernameEditText;

    Disposable disposable;
    private MyLoginView loginView;
    private LoginPresenter presenter;

    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        //Process Bundle
        String username = intent.getStringExtra(EXTRA_USERNAME);
        if (username != null && !username.isEmpty()) {
            username = username.trim();
            usernameEditText.setText(username);
            search(username);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().setTitle("Sign In to Shopping App");
        ButterKnife.bind(this);
        ((MyAbstractApplication) getApplication()).getAppComponent().inject(this);

        loginView = new MyLoginView();
        presenter = new LoginPresenter(loginView);

        handleIntent(getIntent());

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
        String username = usernameEditText.getText().toString();

        presenter.loginClicked();
//        search(username);
    }

    private void search(final String username) {
        gitHubService.user(username).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<GitHubUser>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onNext(GitHubUser gitHubUser) {
                Timber.v(gitHubUser.avatar_url);
                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(intent);
                usernameEditText.setText("");
            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(LoginActivity.this, "Could not find user '" + username + "'", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onComplete() {
                Timber.v("onComplete");
            }
        });
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

    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.onResume(getIntent());
    }
}
