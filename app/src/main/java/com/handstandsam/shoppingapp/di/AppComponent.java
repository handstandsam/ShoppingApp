package com.handstandsam.shoppingapp.di;

import com.handstandsam.shoppingapp.LoggedInActivity;
import com.handstandsam.shoppingapp.features.category.CategoryActivity;
import com.handstandsam.shoppingapp.features.category.CategoryPresenter;
import com.handstandsam.shoppingapp.features.checkout.CheckoutPresenter;
import com.handstandsam.shoppingapp.features.home.HomeActivity;
import com.handstandsam.shoppingapp.features.home.HomePresenter;
import com.handstandsam.shoppingapp.features.itemdetail.ItemDetailPresenter;
import com.handstandsam.shoppingapp.features.login.LoginActivity;
import com.handstandsam.shoppingapp.features.login.LoginPresenter;
import com.handstandsam.shoppingapp.mockaccount.Stubberator;
import com.handstandsam.shoppingapp.repository.CategoryRepository;
import com.handstandsam.shoppingapp.repository.ItemRepository;
import com.handstandsam.shoppingapp.repository.UserRepository;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, NetworkModule.class, RepositoryModule.class})
public interface AppComponent {
    void inject(LoginActivity clazz);

    void inject(HomeActivity clazz);

    void inject(CategoryActivity clazz);

    void inject(Stubberator clazz);

    void inject(HomePresenter clazz);

    void inject(CategoryPresenter clazz);

    void inject(LoginPresenter clazz);

    void inject(LoggedInActivity clazz);

    void inject(ItemDetailPresenter clazz);

    void inject(CheckoutPresenter clazz);

    void inject(CategoryRepository clazz);

    void inject(UserRepository clazz);

    void inject(ItemRepository clazz);


}