package com.handstandsam.shoppingapp.models;


public class CategoryBuilder {

    Category category;

    public CategoryBuilder() {
        category = new Category(null, null, null);
    }

    public CategoryBuilder(String label) {
        this();
        label(label);
    }

    public CategoryBuilder label(String label) {
        category.setLabel(label);
        return this;
    }

    public Category build() {
        return category;
    }

    public CategoryBuilder image(String image) {
        category.setImage(image);
        return this;
    }

    public CategoryBuilder link(String link) {
        category.setLink(link);
        return this;
    }
}
