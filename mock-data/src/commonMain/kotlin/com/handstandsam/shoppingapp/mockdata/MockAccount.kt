package com.handstandsam.shoppingapp.mockdata


import com.handstandsam.shoppingapp.models.Category
import com.handstandsam.shoppingapp.models.Item
import com.handstandsam.shoppingapp.models.User

abstract class MockAccount {

    val itemsByCategory: MutableMap<String, List<Item>?> = mutableMapOf()

    abstract fun getUsername(): String

    abstract fun getUser(): User

    abstract fun getCategories(): List<Category>

    open fun getItemsForCategory(categoryLabel: String): List<Item>? {
        return listOf()
    }
}
