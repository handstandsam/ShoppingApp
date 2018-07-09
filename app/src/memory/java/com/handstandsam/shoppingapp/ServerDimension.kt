package com.handstandsam.shoppingapp

import android.content.Context
import com.handstandsam.shoppingapp.di.NetworkGraph
import com.handstandsam.shoppingapp.di.NetworkGraphImpl
import com.handstandsam.shoppingapp.mockdata.MockAccount
import com.handstandsam.shoppingapp.mockdata.VideoGameMockAccount
import com.handstandsam.shoppingapp.models.Category
import com.handstandsam.shoppingapp.models.Item
import com.handstandsam.shoppingapp.models.LoginRequest
import com.handstandsam.shoppingapp.models.User
import com.handstandsam.shoppingapp.repository.CategoryRepo
import com.handstandsam.shoppingapp.repository.ItemRepo
import com.handstandsam.shoppingapp.repository.UserRepo
import io.reactivex.Single
import timber.log.Timber

val mockAccount: MockAccount = VideoGameMockAccount()

class InMemoryNetworkGraph(appContext: Context) : NetworkGraphImpl(appContext) {
    override val categoryRepo: CategoryRepo by lazy {
        object : CategoryRepo {
            override fun getCategories(): Single<List<Category>> {
                return Single.fromCallable { mockAccount.getCategories() }
            }
        }
    }

    override val itemRepo: ItemRepo by lazy {
        object : ItemRepo {
            override fun getItemsForCategory(categoryLabel: String): Single<List<Item>> {
                return Single.fromCallable { mockAccount.getItemsForCategory(categoryLabel) }
            }
        }
    }

    override val userRepo: UserRepo by lazy {
        object : UserRepo {
            override fun login(loginRequest: LoginRequest): Single<User> {
                return Single.fromCallable { mockAccount.getUser() }
            }

            override fun save(user: User) {
                Timber.d("Doing nothing with this UserRepo.save() for now...")
            }

        }
    }

}

fun MyAbstractApplication.serverDimensionNetworkGraph(): NetworkGraph {
    return InMemoryNetworkGraph(applicationContext)
}