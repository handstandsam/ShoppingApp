package com.handstandsam.shoppingapp.models

import java.io.Serializable

data class Category(val label: String, val image: String, val link: String? = null) : Serializable
