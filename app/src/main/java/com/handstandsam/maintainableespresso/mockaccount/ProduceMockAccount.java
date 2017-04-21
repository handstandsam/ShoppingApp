package com.handstandsam.maintainableespresso.mockaccount;


import com.handstandsam.maintainableespresso.models.Category;
import com.handstandsam.maintainableespresso.models.CategoryBuilder;
import com.handstandsam.maintainableespresso.models.Item;
import com.handstandsam.maintainableespresso.models.ItemBuilder;
import com.handstandsam.maintainableespresso.models.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProduceMockAccount extends MockAccount {

    User user;

    List<Category> categories = new ArrayList<>();

    Map<String, List<Item>> itemByCategoryMap = new HashMap<>();

    public ProduceMockAccount() {

        user = new User("Sam", "Edwards");
        Category fruitCategory = new CategoryBuilder("Fruits").build();
        String[] fruitNames = {"Apple", "Strawberry", "Orange", "Banana", "Grapes", "Pear", "Peach", "Cherry", "Pineapple", "Grapefruit", "Mango", "Watermelon", "Kiwi", "Lemon", "Blackberries"};
        List<Item> fruitItems = new ArrayList<>();
        for (String name : fruitNames) {
            fruitItems.add(new ItemBuilder().label(name).build());
        }
        itemByCategoryMap.put(fruitCategory.getLabel(), fruitItems);


        String[] vegetableNames = {"Carrots", "Romaine Lettuce", "Radish", "Squash", "Celery", "Spinach", "Peas", "Broccoli", "Tomato", "Cabbage", "Onion", "Brussels Sprouts", "Zucchini", "Kale", "Asparagus"};
        List<Item> vegetableItems = new ArrayList<>();
        Category vegetableCategory = new CategoryBuilder("Vegetables").build();
        for (String name : vegetableNames) {
            vegetableItems.add(new ItemBuilder().label(name).build());
        }
        itemByCategoryMap.put(vegetableCategory.getLabel(), vegetableItems);
        categories = Arrays.asList(fruitCategory, vegetableCategory);
    }


    @Override
    public String getUsername() {
        return "produce";
    }

    @Override
    public User getUser() {
        return user;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public List<Item> getItemsForCategory(String categoryLabel) {
        return itemByCategoryMap.get(categoryLabel);
    }
}
