package com.handstandsam.shoppingapp.repository;

import com.handstandsam.shoppingapp.models.User;
import com.handstandsam.shoppingapp.network.ShoppingService;
import com.handstandsam.shoppingapp.models.LoginRequest;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class UserRepository {

    User user;

    @Inject
    ShoppingService shoppingService;

    public UserRepository(ShoppingService shoppingService) {
        this.shoppingService = shoppingService;
    }

    public void save(User user) {
        Timber.d("Saving User: " + user);
        this.user = user;
    }

    public Single<User> login(LoginRequest loginRequest) {
        return shoppingService.login(loginRequest).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }
}
