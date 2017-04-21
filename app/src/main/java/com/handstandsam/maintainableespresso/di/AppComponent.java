package com.handstandsam.maintainableespresso.di;

import com.handstandsam.maintainableespresso.login.LoginActivity;
import com.handstandsam.maintainableespresso.category.CategoryActivity;
import com.handstandsam.maintainableespresso.category.CategoryPresenter;
import com.handstandsam.maintainableespresso.home.HomeActivity;
import com.handstandsam.maintainableespresso.home.HomePresenter;
import com.handstandsam.maintainableespresso.login.LoginPresenter;
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
}