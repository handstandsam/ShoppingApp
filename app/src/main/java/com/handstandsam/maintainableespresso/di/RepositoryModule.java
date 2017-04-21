package com.handstandsam.maintainableespresso.di;

import com.handstandsam.maintainableespresso.repository.CategoryRepository;
import com.handstandsam.maintainableespresso.repository.ItemRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RepositoryModule {

    @Singleton
    @Provides
    CategoryRepository categoryRepository() {
        return new CategoryRepository();
    }


    @Singleton
    @Provides
    ItemRepository itemRepository() {
        return new ItemRepository();
    }
}