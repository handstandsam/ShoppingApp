package com.handstandsam.shoppingapp.models

@kotlinx.serialization.Serializable
data class Item(
    val label: String,
    val image: String,
    val link: String? = null
)
