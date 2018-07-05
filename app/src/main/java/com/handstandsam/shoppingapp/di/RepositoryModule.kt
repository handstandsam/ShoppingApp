package com.handstandsam.shoppingapp.di

import android.content.Context
import com.handstandsam.shoppingapp.network.ShoppingService
import com.handstandsam.shoppingapp.repository.CategoryRepo
import com.handstandsam.shoppingapp.repository.ItemRepo
import com.handstandsam.shoppingapp.repository.UserRepo
import com.handstandsam.shoppingapp.repository.NetworkCategoryRepo
import com.handstandsam.shoppingapp.repository.NetworkItemRepo
import com.handstandsam.shoppingapp.repository.NetworkUserRepo
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule(internal var applicationContext: Context) {

    @Singleton
    @Provides
    internal fun categoryRepository(shoppingService: ShoppingService): CategoryRepo {
        return NetworkCategoryRepo(shoppingService)
    }


    @Singleton
    @Provides
    internal fun itemRepository(shoppingService: ShoppingService): ItemRepo {
        return NetworkItemRepo(shoppingService)
    }

    @Singleton
    @Provides
    internal fun userRepository(shoppingService: ShoppingService): UserRepo {
        return NetworkUserRepo(shoppingService)
    }
}