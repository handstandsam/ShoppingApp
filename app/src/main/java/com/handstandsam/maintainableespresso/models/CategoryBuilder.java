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

    public CategoryBuilder image(String image) {
        category.image = image;
        return this;
    }

    public CategoryBuilder link(String link) {
        category.link = link;
        return this;
    }
}
