package com.handstandsam.shoppingapp.models

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(val username: String, val password: String)
