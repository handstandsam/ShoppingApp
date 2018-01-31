package com.handstandsam.shoppingapp.models

import java.io.Serializable

data class LoginRequest(var username: String, var password: String) : Serializable
