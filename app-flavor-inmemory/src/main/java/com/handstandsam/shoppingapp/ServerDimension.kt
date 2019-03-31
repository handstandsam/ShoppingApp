package com.handstandsam.shoppingapp

import android.app.Application
import com.handstandsam.shoppingapp.di.NetworkGraph
import com.handstandsam.shoppingapp.mockdata.AndroidLibsMockAccount
import com.handstandsam.shoppingapp.mockdata.MockAccount
import com.handstandsam.shoppingapp.models.Category
import com.handstandsam.shoppingapp.models.Item
import com.handstandsam.shoppingapp.models.LoginRequest
import com.handstandsam.shoppingapp.models.User
import com.handstandsam.shoppingapp.repository.CategoryRepo
import com.handstandsam.shoppingapp.repository.ItemRepo
import com.handstandsam.shoppingapp.repository.UserRepo

val mockAccount: MockAccount = AndroidLibsMockAccount()

class InMemoryNetworkGraph : NetworkGraph {
    override val categoryRepo: CategoryRepo =
        object : CategoryRepo {
            override suspend fun getCategories(): List<Category> {
                return mockAccount.getCategories()
            }
        }


    override val itemRepo: ItemRepo =
        object : ItemRepo {
            override suspend fun getItemsForCategory(categoryLabel: String): List<Item>? {
                return mockAccount.getItemsForCategory(categoryLabel)
            }
        }

    override val userRepo: UserRepo =
        object : UserRepo {
            override suspend fun login(loginRequest: LoginRequest): User {
                return mockAccount.getUser()
            }
        }

}

fun Application.serverDimensionNetworkGraph(): NetworkGraph {
    return InMemoryNetworkGraph()
}