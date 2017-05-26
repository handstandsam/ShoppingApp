package com.handstandsam.shoppingapp.network.model;

import java.io.Serializable;

public class LoginRequest implements Serializable {

    public String username;

    public String password;

    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
