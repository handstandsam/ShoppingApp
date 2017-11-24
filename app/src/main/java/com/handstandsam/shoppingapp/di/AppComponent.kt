package com.handstandsam.shoppingapp.di

import com.handstandsam.shoppingapp.LoggedInActivity
import com.handstandsam.shoppingapp.features.category.CategoryActivity
import com.handstandsam.shoppingapp.features.category.CategoryPresenter
import com.handstandsam.shoppingapp.features.checkout.CheckoutPresenter
import com.handstandsam.shoppingapp.features.home.HomeActivity
import com.handstandsam.shoppingapp.features.home.HomePresenter
import com.handstandsam.shoppingapp.features.itemdetail.ItemDetailPresenter
import com.handstandsam.shoppingapp.features.login.LoginActivity
import com.handstandsam.shoppingapp.features.login.LoginPresenter
import com.handstandsam.shoppingapp.mockaccount.Stubberator
import com.handstandsam.shoppingapp.repository.CategoryRepository
import com.handstandsam.shoppingapp.repository.ItemRepository
import com.handstandsam.shoppingapp.repository.UserRepository
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class, NetworkModule::class, RepositoryModule::class))
interface AppComponent {
    fun inject(clazz: LoginActivity)

    fun inject(clazz: HomeActivity)

    fun inject(clazz: CategoryActivity)

    fun inject(clazz: Stubberator)

    fun inject(clazz: HomePresenter)

    fun inject(clazz: CategoryPresenter)

    fun inject(clazz: LoginPresenter)

    fun inject(clazz: LoggedInActivity)

    fun inject(clazz: ItemDetailPresenter)

    fun inject(clazz: CheckoutPresenter)

    fun inject(clazz: CategoryRepository)

    fun inject(clazz: UserRepository)

    fun inject(clazz: ItemRepository)


}