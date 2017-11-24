package com.handstandsam.shoppingapp.di

import android.content.Context
import com.handstandsam.shoppingapp.network.ShoppingService
import com.handstandsam.shoppingapp.repository.CategoryRepository
import com.handstandsam.shoppingapp.repository.ItemRepository
import com.handstandsam.shoppingapp.repository.UserRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule(internal var applicationContext: Context) {

    @Singleton
    @Provides
    internal fun categoryRepository(): CategoryRepository {
        return CategoryRepository(applicationContext)
    }


    @Singleton
    @Provides
    internal fun itemRepository(shoppingService: ShoppingService): ItemRepository {
        return ItemRepository(shoppingService)
    }

    @Singleton
    @Provides
    internal fun userRepository(shoppingService: ShoppingService): UserRepository {
        return UserRepository(shoppingService)
    }
}