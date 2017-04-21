package com.handstandsam.maintainableespresso.category;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.google.gson.GsonBuilder;
import com.handstandsam.maintainableespresso.MyAbstractApplication;
import com.handstandsam.maintainableespresso.models.Category;
import com.handstandsam.maintainableespresso.models.Item;
import com.handstandsam.maintainableespresso.repository.CategoryRepository;
import com.handstandsam.maintainableespresso.repository.ItemRepository;
import com.handstandsam.maintainableespresso.repository.SessionManager;

import java.util.List;

import javax.inject.Inject;

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
        Timber.d("HomePresenter: " + new GsonBuilder().create().toJson(categoryRepository));
    }

    public void onResume(Intent intent) {
        Bundle extras = intent.getExtras();
        Category category = (Category) extras.get(BUNDLE_PARAM_CATEGORY);
        List<Item> items = itemRepository.getItemsForCategory(category.getLabel());
        view.setActionBarTitle(category.getLabel());
        view.showItems(items);
    }
}
