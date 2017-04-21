package com.handstandsam.maintainableespresso.repository;

import com.handstandsam.maintainableespresso.models.Category;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import timber.log.Timber;

public class CategoryRepository {

    Map<String, Category> categoryMap = new HashMap<>();

    public CategoryRepository() {

    }

    public void save(List<Category> categories) {
        Timber.d("Saving Categories " + categories);
        synchronized (categoryMap) {
            for (Category category : categories) {
                categoryMap.put(category.getLabel(), category);
            }
        }
    }

    public List<Category> getCategories() {
        return new ArrayList<>(categoryMap.values());
    }
}
