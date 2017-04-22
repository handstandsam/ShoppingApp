package com.handstandsam.maintainableespresso.repository;


import com.handstandsam.maintainableespresso.models.User;
import com.handstandsam.maintainableespresso.preferences.UserPreferences;

import timber.log.Timber;

public class SessionManager {

    private final CheckoutCart cart;
    private final UserPreferences userPreferences;

    public SessionManager(CheckoutCart cart, UserPreferences userPreferences) {
        this.cart = cart;
        this.userPreferences = userPreferences;
    }

    public User getCurrentUser() {
        return userPreferences.getCurrentUser();
    }

    public void setCurrentUser(User user) {
        Timber.d("setCurrentUser: " + user);
        userPreferences.setCurrentUser(user);
    }

    public void logout() {
        setCurrentUser(null);
        cart.empty();
    }

    public boolean isLoggedIn() {
        boolean loggedIn = getCurrentUser() != null;
        Timber.d("isLoggedIn: " + loggedIn);
        return loggedIn;
    }
}
