package com.handstandsam.maintainableespresso.mockaccount;


import com.handstandsam.maintainableespresso.models.Category;
import com.handstandsam.maintainableespresso.models.Item;
import com.handstandsam.maintainableespresso.models.User;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public abstract class MockAccount {

    protected Map<String, List<Item>> itemByCategoryMap = new LinkedHashMap<>();

    public abstract String getUsername();

    public abstract User getUser();

    public List<Category> getCategories() {
        return new ArrayList<>();
    }

    public List<Item> getItemsForCategory(String categoryLabel) {
        return new ArrayList<>();
    }

    public Map<String, List<Item>> getItemsByCategory() {
        return itemByCategoryMap;
    }
}
