package com.handstandsam.maintainableespresso.di;

import com.handstandsam.maintainableespresso.home.HomeActivity;
import com.handstandsam.maintainableespresso.LoginActivity;
import com.handstandsam.maintainableespresso.category.CategoryActivity;

import dagger.Component;

@Component(modules = {AppModule.class, NetworkModule.class})
public interface AppComponent {
    void inject(LoginActivity clazz);

    void inject(HomeActivity clazz);

    void inject(CategoryActivity clazz);
}