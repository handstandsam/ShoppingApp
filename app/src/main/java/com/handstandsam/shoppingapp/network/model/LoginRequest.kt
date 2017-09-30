package com.handstandsam.shoppingapp.network.model

import java.io.Serializable

data class LoginRequest(var username: String, var password: String) : Serializable
