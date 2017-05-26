package com.handstandsam.shoppingapp.repository;

import com.handstandsam.shoppingapp.models.Item;
import com.handstandsam.shoppingapp.network.ShoppingService;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

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
