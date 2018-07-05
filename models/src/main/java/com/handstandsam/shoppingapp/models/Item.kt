package com.handstandsam.shoppingapp.models

import java.io.Serializable

data class Item(val label: String, val image: String, val link: String? = null): Serializable
