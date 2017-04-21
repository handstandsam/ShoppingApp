package com.handstandsam.maintainableespresso.features.login;

import android.content.Intent;
import android.support.annotation.NonNull;

import com.handstandsam.maintainableespresso.mockaccount.ProduceMockAccount;
import com.handstandsam.maintainableespresso.preferences.UserPreferences;
import com.handstandsam.maintainableespresso.repository.SessionManager;

import javax.inject.Inject;

public class LoginPresenter {

    private final LoginActivity.LoginView view;

    @Inject
    SessionManager sessionManager;

    @Inject
    UserPreferences userPreferences;

    public LoginPresenter(@NonNull LoginActivity.LoginView view) {
        this.view = view;
        view.getAppComponent().inject(this);
    }

    public void onResume(Intent intent) {

        if (sessionManager.isLoggedIn()) {
            view.kickToHomeScreen();
        }

        //Nothing for now...
        String username = userPreferences.getLastLoggedInUsername();
        if (username != null) {
            view.setUsername(username);
        }

        view.setRememberMe(userPreferences.getRememberMe());
    }

    public void loginClicked() {
        userPreferences.setIsLoggedIn(true);
        boolean rememberMe = view.isRememberMeChecked();
        if (rememberMe) {
            userPreferences.setLastLoggedInUsername(view.getUsername());
        } else {
            userPreferences.setLastLoggedInUsername(null);
        }
        userPreferences.setRememberMe(rememberMe);

        sessionManager.setCurrentUser(new ProduceMockAccount().getUser());

        view.startHomeActivity();
    }
}
