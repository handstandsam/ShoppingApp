package com.handstandsam.maintainableespresso.di;

import com.handstandsam.maintainableespresso.LoggedInActivity;
import com.handstandsam.maintainableespresso.features.category.CategoryActivity;
import com.handstandsam.maintainableespresso.features.category.CategoryPresenter;
import com.handstandsam.maintainableespresso.features.checkout.CheckoutPresenter;
import com.handstandsam.maintainableespresso.features.home.HomeActivity;
import com.handstandsam.maintainableespresso.features.home.HomePresenter;
import com.handstandsam.maintainableespresso.features.itemdetail.ItemDetailPresenter;
import com.handstandsam.maintainableespresso.features.login.LoginActivity;
import com.handstandsam.maintainableespresso.features.login.LoginPresenter;
import com.handstandsam.maintainableespresso.mockaccount.Stubberator;

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
}