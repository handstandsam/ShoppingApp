package com.handstandsam.maintainableespresso.repository;

import com.handstandsam.maintainableespresso.models.User;

public class SessionManager {

    User user;

    public SessionManager() {

    }

    public User getCurrentUser() {
        return user;
    }

    public void setCurrentUser(User user) {
        this.user = user;
    }

    public void logout() {
        this.user = null;
    }

    public boolean isLoggedIn() {
        return user != null;
    }
}
