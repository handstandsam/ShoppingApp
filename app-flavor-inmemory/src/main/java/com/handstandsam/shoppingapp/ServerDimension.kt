package com.handstandsam.shoppingapp

import android.app.Application
import com.handstandsam.shoppingapp.di.NetworkGraph
import com.handstandsam.shoppingapp.mockdata.AndroidLibsMockAccount
import com.handstandsam.shoppingapp.mockdata.MockAccount
import com.handstandsam.shoppingapp.models.LoginRequest
import com.handstandsam.shoppingapp.repository.CategoriesResult
import com.handstandsam.shoppingapp.repository.CategoryRepo
import com.handstandsam.shoppingapp.repository.ItemRepo
import com.handstandsam.shoppingapp.repository.ItemsForCategoryResult
import com.handstandsam.shoppingapp.repository.UserRepo
import com.handstandsam.shoppingapp.repository.UserResult

val mockAccount: MockAccount = AndroidLibsMockAccount()

class InMemoryNetworkGraph : NetworkGraph {
    override val categoryRepo: CategoryRepo =
        object : CategoryRepo {
            override suspend fun getCategories(): CategoriesResult {
                return CategoriesResult.Success(mockAccount.getCategories())
            }
        }


    override val itemRepo: ItemRepo =
        object : ItemRepo {
            override suspend fun getItemsForCategory(categoryLabel: String): ItemsForCategoryResult {
                val itemsForCategory = mockAccount.getItemsForCategory(categoryLabel)
                if (itemsForCategory != null) {
                    return ItemsForCategoryResult.Success(itemsForCategory)
                } else {
                    return ItemsForCategoryResult.Failure()
                }
            }
        }

    override val userRepo: UserRepo =
        object : UserRepo {
            override suspend fun login(loginRequest: LoginRequest): UserResult {
                return UserResult.Success(mockAccount.getUser())
            }
        }

}

fun Application.serverDimensionNetworkGraph(): NetworkGraph {
    return InMemoryNetworkGraph()
}