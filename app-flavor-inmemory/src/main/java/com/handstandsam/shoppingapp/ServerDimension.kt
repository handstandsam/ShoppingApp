package com.handstandsam.shoppingapp

import android.app.Application
import com.handstandsam.shoppingapp.di.NetworkGraph
import com.handstandsam.shoppingapp.mockdata.MockAccount
import com.handstandsam.shoppingapp.mockdata.ProduceMockAccount
import com.handstandsam.shoppingapp.models.Category
import com.handstandsam.shoppingapp.models.Item
import com.handstandsam.shoppingapp.models.LoginRequest
import com.handstandsam.shoppingapp.models.User
import com.handstandsam.shoppingapp.repository.CategoryRepo
import com.handstandsam.shoppingapp.repository.ItemRepo
import com.handstandsam.shoppingapp.repository.NetworkResult
import com.handstandsam.shoppingapp.repository.UserRepo
import okhttp3.Interceptor

val mockAccount: MockAccount = ProduceMockAccount()

class InMemoryNetworkGraph : NetworkGraph {
    override val categoryRepo: CategoryRepo =
        object : CategoryRepo {
            override suspend fun getCategories(): NetworkResult<List<Category>> {
                return NetworkResult.Success(mockAccount.getCategories())
            }
        }


    override val itemRepo: ItemRepo =
        object : ItemRepo {
            override suspend fun getItemsForCategory(categoryLabel: String): NetworkResult<List<Item>> {
                val itemsForCategory = mockAccount.getItemsForCategory(categoryLabel)
                if (itemsForCategory != null) {
                    return NetworkResult.Success(itemsForCategory)
                } else {
                    return NetworkResult.Failure()
                }
            }
        }

    override val userRepo: UserRepo =
        object : UserRepo {
            override suspend fun login(loginRequest: LoginRequest): NetworkResult<User> {
                return NetworkResult.Success(mockAccount.getUser())
            }
        }

}

fun Application.serverDimensionNetworkGraph(interceptors: List<Interceptor>): NetworkGraph {
    return InMemoryNetworkGraph()
}