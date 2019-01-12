package com.handstandsam.shoppingapp

import android.content.Context
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
import io.reactivex.Single
import timber.log.Timber

val mockAccount: MockAccount = AndroidLibsMockAccount()

class InMemoryNetworkGraph() : NetworkGraph {
    override val categoryRepo: CategoryRepo =
        object : CategoryRepo {
            override fun getCategories(): Single<List<Category>> {
                return Single.fromCallable { mockAccount.getCategories() }
            }
        }


    override val itemRepo: ItemRepo =
        object : ItemRepo {
            override fun getItemsForCategory(categoryLabel: String): Single<List<Item>> {
                return Single.fromCallable { mockAccount.getItemsForCategory(categoryLabel) }
            }
        }

    override val userRepo: UserRepo =
        object : UserRepo {
            override fun login(loginRequest: LoginRequest): Single<User> {
                return Single.fromCallable { mockAccount.getUser() }
            }
        }

}

fun MyAbstractApplication.serverDimensionNetworkGraph(): NetworkGraph {
    return InMemoryNetworkGraph()
}