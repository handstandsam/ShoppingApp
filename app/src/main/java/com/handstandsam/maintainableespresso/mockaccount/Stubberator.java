package com.handstandsam.maintainableespresso.mockaccount;


import android.content.Context;

import com.google.gson.GsonBuilder;
import com.handstandsam.maintainableespresso.MyAbstractApplication;
import com.handstandsam.maintainableespresso.models.Category;
import com.handstandsam.maintainableespresso.repository.CategoryRepository;
import com.handstandsam.maintainableespresso.repository.ItemRepository;
import com.handstandsam.maintainableespresso.repository.SessionManager;

import javax.inject.Inject;

import timber.log.Timber;

public class Stubberator {

    @Inject
    ItemRepository itemRepository;

    @Inject
    CategoryRepository categoryRepository;

    @Inject
    SessionManager sessionManager;

    Context applicationContext;

    public Stubberator(Context context) {
        Timber.d("new Stubberator()");
        this.applicationContext = context.getApplicationContext();
        ((MyAbstractApplication) applicationContext).getAppComponent().inject(this);
    }

    public void stubItAll(MockAccount mockAccount) {
        Timber.d("stubItAll MockAccount: "+ new GsonBuilder().create().toJson(mockAccount));
        categoryRepository.save(mockAccount.getCategories());
        for (Category category : mockAccount.getCategories()) {
            String categoryLabel = category.getLabel();
            itemRepository.save(categoryLabel, mockAccount.getItemsForCategory(categoryLabel));
        }
    }

}
