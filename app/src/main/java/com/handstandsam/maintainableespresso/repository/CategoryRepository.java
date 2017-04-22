package com.handstandsam.maintainableespresso.repository;

import com.handstandsam.maintainableespresso.models.Category;

import java.util.List;

public class CategoryRepository {

    private List<Category> categories;

    public void save(List<Category> categories) {
        this.categories = categories;
    }

    public List<Category> getCategories() {
        return categories;
    }
}
