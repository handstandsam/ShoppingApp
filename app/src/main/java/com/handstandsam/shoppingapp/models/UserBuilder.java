package com.handstandsam.shoppingapp.models;


public class UserBuilder {

    User user;

    public UserBuilder(String firstname, String lastname) {
        user = new User(firstname, lastname);
    }

    public User build() {
        return user;
    }
}
