package com.handstandsam.shoppingapp.models

import kotlinx.serialization.Serializable

@Serializable
data class User(val firstname: String, val lastname: String)
