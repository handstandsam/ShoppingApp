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

        Category fruitCategory = new CategoryBuilder("Fruits").image("fuji-apple.jpg").build();
        List<Item> fruitItems = new ArrayList<>();
        fruitItems.add(new ItemBuilder("Granny Smith Apple").image("granny-smith-apple.jpg").build());
        fruitItems.add(new ItemBuilder("Gala Apple").image("gala-apple.jpg").build());
        fruitItems.add(new ItemBuilder("Pineapple").image("pineapple.jpg").build());
        fruitItems.add(new ItemBuilder("Cucumber").image("cucumber.jpg").build());
        fruitItems.add(new ItemBuilder("Red Delicious Apple").image("red-delicious-apple.jpg").build());
        fruitItems.add(new ItemBuilder("Fuji Apple").image("fuji-apple.jpg").build());
        fruitItems.add(new ItemBuilder("Lemon").image("lemons.jpg").build());
        fruitItems.add(new ItemBuilder("Grapefruit").image("grapefruit.jpg").build());
        fruitItems.add(new ItemBuilder("Lime").image("lime.jpg").build());
        fruitItems.add(new ItemBuilder("Orange").image("orange.jpg").build());
        itemByCategoryMap.put(fruitCategory.getLabel(), fruitItems);

        Category floralCategory = new CategoryBuilder("Floral").image("white-rose.jpg").build();
        List<Item> floralItems = new ArrayList<>();
        floralItems.add(new ItemBuilder("White Rose").image("white-rose.jpg").build());
        floralItems.add(new ItemBuilder("Sunflower").image("sunflower.jpg").build());
        itemByCategoryMap.put(floralCategory.getLabel(), floralItems);

        Category seafoodCategory = new CategoryBuilder("Seafood").image("shrimp.jpg").build();
        List<Item> seafoodItems = new ArrayList<>();
        seafoodItems.add(new ItemBuilder("Lobster Tail").image("lobster-tail.jpg").build());
        seafoodItems.add(new ItemBuilder("Shrimp").image("shrimp.jpg").build());
        seafoodItems.add(new ItemBuilder("Scallop").image("scallops.jpg").build());
        itemByCategoryMap.put(seafoodCategory.getLabel(), seafoodItems);

        Category vegetableCategory = new CategoryBuilder("Vegetables").image("broccoli.jpg").build();
        List<Item> vegetableItems = new ArrayList<>();
        vegetableItems.add(new ItemBuilder("Kale").image("kale.jpg").build());
        vegetableItems.add(new ItemBuilder("Romaine Lettuce").image("romaine-lettuce.jpg").build());
        vegetableItems.add(new ItemBuilder("Artichoke").image("artichoke.jpg").build());
        vegetableItems.add(new ItemBuilder("Beet").image("beet.jpg").build());
        vegetableItems.add(new ItemBuilder("Radish").image("radish.jpg").build());
        vegetableItems.add(new ItemBuilder("Tomato").image("tomato.jpg").build());
        vegetableItems.add(new ItemBuilder("Broccoli").image("broccoli.jpg").build());
        vegetableItems.add(new ItemBuilder("Avocado").image("avocado.jpg").build());
        vegetableItems.add(new ItemBuilder("Red Bell Pepper").image("red-bell-pepper.jpg").build());
        vegetableItems.add(new ItemBuilder("Orange Bell Pepper").image("orange-bell-pepper.jpg").build());
        vegetableItems.add(new ItemBuilder("Yellow Bell Pepper").image("yellow-bell-pepper.jpg").build());
        vegetableItems.add(new ItemBuilder("Carrot").image("carrots.jpg").build());
        itemByCategoryMap.put(vegetableCategory.getLabel(), vegetableItems);

        categories = Arrays.asList(fruitCategory, vegetableCategory, seafoodCategory, floralCategory);
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
