package com.handstandsam.maintainableespresso.login;

import android.content.Intent;
import android.support.annotation.NonNull;

import com.handstandsam.maintainableespresso.home.HomeActivity;
import com.handstandsam.maintainableespresso.repository.SessionManager;

import javax.inject.Inject;

public class LoginPresenter {

    private final LoginActivity.LoginView view;

    @Inject
    SessionManager sessionManager;

    public LoginPresenter(@NonNull LoginActivity.LoginView view) {
        this.view = view;
        view.getAppComponent().inject(this);
    }

    public void onResume(Intent intent) {
        //Nothing for now...
    }

    public void loginClicked() {
        Intent intent = new Intent(view.getActivity(), HomeActivity.class);
        view.startActivity(intent);
    }
}
