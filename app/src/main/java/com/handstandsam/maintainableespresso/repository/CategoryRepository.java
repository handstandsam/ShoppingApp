package com.handstandsam.maintainableespresso.repository;

import android.content.Context;

import com.handstandsam.maintainableespresso.MyAbstractApplication;
import com.handstandsam.maintainableespresso.models.Category;
import com.handstandsam.maintainableespresso.network.ShoppingService;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CategoryRepository {

    private List<Category> categories;

    @Inject
    ShoppingService shoppingService;

    public CategoryRepository(Context applicationContext) {
        ((MyAbstractApplication) applicationContext).getAppComponent().inject(this);
    }

    public Single<List<Category>> getCategories() {
        return shoppingService.getCategories().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }
}
