package com.handstandsam.maintainableespresso.repository;

import com.handstandsam.maintainableespresso.models.Item;
import com.handstandsam.maintainableespresso.network.ShoppingService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class ItemRepository {

    @Inject
    ShoppingService shoppingService;

    public ItemRepository(ShoppingService shoppingService) {
        this.shoppingService = shoppingService;
    }

    public Single<List<Item>> getItemsForCategory(String categoryLabel) {
        return shoppingService.getItemsForCategory(categoryLabel).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }
}
