package com.handstandsam.maintainableespresso.mockaccount;


import com.handstandsam.maintainableespresso.models.Category;
import com.handstandsam.maintainableespresso.models.Item;
import com.handstandsam.maintainableespresso.models.User;

import java.util.ArrayList;
import java.util.List;

public abstract class MockAccount {

    public abstract String getUsername();

    public abstract User getUser();

    public List<Category> getCategories(){
        return new ArrayList<>();
    }

    public List<Item> getItemsForCategory(String categoryLabel){
        return new ArrayList<>();
    }
}
