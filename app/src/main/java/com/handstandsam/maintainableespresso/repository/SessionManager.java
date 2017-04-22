package com.handstandsam.maintainableespresso.repository;

import com.handstandsam.maintainableespresso.models.User;

import timber.log.Timber;

public class SessionManager {

    private final CheckoutCart cart;

    User user;

    public SessionManager(CheckoutCart cart) {
        this.cart = cart;
    }

    public User getCurrentUser() {
        return user;
    }

    public void setCurrentUser(User user) {
        Timber.d("setCurrentUser: " + user);
        this.user = user;
    }

    public void logout() {
        this.user = null;
        cart.empty();
    }

    public boolean isLoggedIn() {
        Timber.d("isLoggedIn: " + user);
        return user != null;
    }
}
