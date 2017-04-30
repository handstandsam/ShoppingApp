package com.handstandsam.maintainableespresso.di;

import android.content.Context;

import com.handstandsam.maintainableespresso.network.ShoppingService;
import com.handstandsam.maintainableespresso.repository.CategoryRepository;
import com.handstandsam.maintainableespresso.repository.ItemRepository;
import com.handstandsam.maintainableespresso.repository.UserRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RepositoryModule {

    Context applicationContext;

    public RepositoryModule(Context applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Singleton
    @Provides
    CategoryRepository categoryRepository() {
        return new CategoryRepository(applicationContext);
    }


    @Singleton
    @Provides
    ItemRepository itemRepository(ShoppingService shoppingService) {
        return new ItemRepository(shoppingService);
    }

    @Singleton
    @Provides
    UserRepository userRepository(ShoppingService shoppingService) {
        return new UserRepository(shoppingService);
    }
}