package com.handstandsam.shoppingapp.models

@kotlinx.serialization.Serializable
data class Category(val label: String, val image: String, val link: String? = null)
