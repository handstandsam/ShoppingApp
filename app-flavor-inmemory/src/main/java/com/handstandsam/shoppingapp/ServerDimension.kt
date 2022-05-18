package com.handstandsam.shoppingapp

import com.handstandsam.shoppingapp.di.NetworkGraph
import com.handstandsam.shoppingapp.mockdata.AndroidLibsMockAccount
import com.handstandsam.shoppingapp.mockdata.MockAccount
import com.handstandsam.shoppingapp.mockdata.ProduceMockAccount
import com.handstandsam.shoppingapp.mockdata.VideoGameMockAccount
import com.handstandsam.shoppingapp.models.Category
import com.handstandsam.shoppingapp.models.Item
import com.handstandsam.shoppingapp.models.LoginRequest
import com.handstandsam.shoppingapp.models.User
import com.handstandsam.shoppingapp.network.Response
import com.handstandsam.shoppingapp.repository.CategoryRepo
import com.handstandsam.shoppingapp.repository.ItemRepo
import com.handstandsam.shoppingapp.repository.UserRepo
import okhttp3.Interceptor

val mockAccount: MockAccount = ProduceMockAccount()

class InMemoryNetworkGraph : NetworkGraph {
    override val categoryRepo: CategoryRepo =
        object : CategoryRepo {
            override suspend fun getCategories(): Response<List<Category>> {
                val categories = mockAccount.getCategories()
                return Response.Success(categories)
            }
        }

    override val itemRepo: ItemRepo =
        object : ItemRepo {
            override suspend fun getItemsForCategory(categoryLabel: String): Response<List<Item>> {
                val itemsForCategory = mockAccount.getItemsForCategory(categoryLabel)
                if (itemsForCategory != null) {
                    return Response.Success(itemsForCategory)
                } else {
                    return Response.Failure()
                }
            }
        }

    override val userRepo: UserRepo =
        object : UserRepo {
            override suspend fun login(loginRequest: LoginRequest): Response<User> {
                return Response.Success(mockAccount.getUser())
            }
        }
}

fun serverDimensionNetworkGraph(interceptors: List<Interceptor>): NetworkGraph {
    return InMemoryNetworkGraph()
}