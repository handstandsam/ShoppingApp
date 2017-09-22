package com.handstandsam.shoppingapp.features.category;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.handstandsam.shoppingapp.MyAbstractApplication;
import com.handstandsam.shoppingapp.models.Category;
import com.handstandsam.shoppingapp.models.Item;
import com.handstandsam.shoppingapp.repository.CategoryRepository;
import com.handstandsam.shoppingapp.repository.ItemRepository;
import com.handstandsam.shoppingapp.repository.SessionManager;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;

public class CategoryPresenter {

    public static final String BUNDLE_PARAM_CATEGORY = "category";

    private final Context applicationContext;
    private final CategoryActivity.CategoryView view;

    @Inject
    SessionManager sessionManager;

    @Inject
    CategoryRepository categoryRepository;

    @Inject
    ItemRepository itemRepository;

    public CategoryPresenter(@NonNull CategoryActivity.CategoryView homeView) {
        this.view = homeView;
        this.applicationContext = homeView.getContext();
        ((MyAbstractApplication) applicationContext).getAppComponent().inject(this);
    }

    public void onResume(Intent intent) {
        Bundle extras = intent.getExtras();
        Category category = (Category) extras.get(BUNDLE_PARAM_CATEGORY);
        view.setActionBarTitle(category.getLabel());
        itemRepository.getItemsForCategory(category.getLabel()).subscribe(new SingleObserver<List<Item>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(List<Item> items) {
                view.showItems(items);
            }

            @Override
            public void onError(Throwable e) {
                Timber.w("Networking Error", e);
                Timber.w(e.getMessage());
                Timber.w(e);
                view.showNetworkError();
            }
        });
    }
}
