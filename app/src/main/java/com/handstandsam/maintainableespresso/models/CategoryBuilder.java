package com.handstandsam.maintainableespresso.models;


public class CategoryBuilder {

    Category category;

    public CategoryBuilder() {
        category = new Category();
    }

    public CategoryBuilder(String label) {
        this();
        label(label);
    }

    public CategoryBuilder label(String label) {
        category.label = label;
        return this;
    }

    public Category build() {
        return category;
    }
}
