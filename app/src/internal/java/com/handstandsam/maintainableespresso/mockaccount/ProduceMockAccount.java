package com.handstandsam.maintainableespresso.mockaccount;


import com.handstandsam.maintainableespresso.di.NetworkModule;
import com.handstandsam.maintainableespresso.models.Category;
import com.handstandsam.maintainableespresso.models.CategoryBuilder;
import com.handstandsam.maintainableespresso.models.Item;
import com.handstandsam.maintainableespresso.models.ItemBuilder;
import com.handstandsam.maintainableespresso.models.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProduceMockAccount extends MockAccount {

    User user;

    List<Category> categories = new ArrayList<>();

    public ProduceMockAccount() {
        user = new User("Sam", "Edwards");

        Category fruitCategory = new CategoryBuilder("Fruits").image(NetworkModule.IMAGE_BASE_URL + "fuji-apple.jpg").build();
        List<Item> fruitItems = new ArrayList<>();
        fruitItems.add(new ItemBuilder("Granny Smith Apple").image(NetworkModule.IMAGE_BASE_URL + "granny-smith-apple.jpg").build());
        fruitItems.add(new ItemBuilder("Gala Apple").image(NetworkModule.IMAGE_BASE_URL + "gala-apple.jpg").build());
        fruitItems.add(new ItemBuilder("Pineapple").image(NetworkModule.IMAGE_BASE_URL + "pineapple.jpg").build());
        fruitItems.add(new ItemBuilder("Red Delicious Apple").image(NetworkModule.IMAGE_BASE_URL + "red-delicious-apple.jpg").build());
        fruitItems.add(new ItemBuilder("Fuji Apple").image(NetworkModule.IMAGE_BASE_URL + "fuji-apple.jpg").build());
        fruitItems.add(new ItemBuilder("Lemon").image(NetworkModule.IMAGE_BASE_URL + "lemons.jpg").build());
        fruitItems.add(new ItemBuilder("Grapefruit").image(NetworkModule.IMAGE_BASE_URL + "grapefruit.jpg").build());
        fruitItems.add(new ItemBuilder("Lime").image(NetworkModule.IMAGE_BASE_URL + "lime.jpg").build());
        fruitItems.add(new ItemBuilder("Orange").image(NetworkModule.IMAGE_BASE_URL + "orange.jpg").build());
        itemByCategoryMap.put(fruitCategory.getLabel(), fruitItems);

        Category floralCategory = new CategoryBuilder("Floral").image(NetworkModule.IMAGE_BASE_URL + "white-rose.jpg").build();
        List<Item> floralItems = new ArrayList<>();
        floralItems.add(new ItemBuilder("White Rose").image(NetworkModule.IMAGE_BASE_URL + "white-rose.jpg").build());
        floralItems.add(new ItemBuilder("Sunflower").image(NetworkModule.IMAGE_BASE_URL + "sunflower.jpg").build());
        itemByCategoryMap.put(floralCategory.getLabel(), floralItems);

        Category seafoodCategory = new CategoryBuilder("Seafood").image(NetworkModule.IMAGE_BASE_URL + "shrimp.jpg").build();
        List<Item> seafoodItems = new ArrayList<>();
        seafoodItems.add(new ItemBuilder("Lobster Tail").image(NetworkModule.IMAGE_BASE_URL + "lobster-tail.jpg").build());
        seafoodItems.add(new ItemBuilder("Shrimp").image(NetworkModule.IMAGE_BASE_URL + "shrimp.jpg").build());
        seafoodItems.add(new ItemBuilder("Scallop").image(NetworkModule.IMAGE_BASE_URL + "scallops.jpg").build());
        itemByCategoryMap.put(seafoodCategory.getLabel(), seafoodItems);

        Category vegetableCategory = new CategoryBuilder("Vegetables").image(NetworkModule.IMAGE_BASE_URL + "kale.jpg").build();
        List<Item> vegetableItems = new ArrayList<>();
        vegetableItems.add(new ItemBuilder("Carrot").image(NetworkModule.IMAGE_BASE_URL + "carrots.jpg").build());
        vegetableItems.add(new ItemBuilder("Cucumber").image(NetworkModule.IMAGE_BASE_URL + "cucumber.jpg").build());
        vegetableItems.add(new ItemBuilder("Kale").image(NetworkModule.IMAGE_BASE_URL + "kale.jpg").build());
        vegetableItems.add(new ItemBuilder("Romaine Lettuce").image(NetworkModule.IMAGE_BASE_URL + "romaine-lettuce.jpg").build());
        vegetableItems.add(new ItemBuilder("Artichoke").image(NetworkModule.IMAGE_BASE_URL + "artichoke.jpg").build());
        vegetableItems.add(new ItemBuilder("Beet").image(NetworkModule.IMAGE_BASE_URL + "beet.jpg").build());
        vegetableItems.add(new ItemBuilder("Radish").image(NetworkModule.IMAGE_BASE_URL + "radish.jpg").build());
        vegetableItems.add(new ItemBuilder("Tomato").image(NetworkModule.IMAGE_BASE_URL + "tomato.jpg").build());
        vegetableItems.add(new ItemBuilder("Broccoli").image(NetworkModule.IMAGE_BASE_URL + "broccoli.jpg").build());
        vegetableItems.add(new ItemBuilder("Avocado").image(NetworkModule.IMAGE_BASE_URL + "avocado.jpg").build());
        vegetableItems.add(new ItemBuilder("Red Bell Pepper").image(NetworkModule.IMAGE_BASE_URL + "red-bell-pepper.jpg").build());
        vegetableItems.add(new ItemBuilder("Orange Bell Pepper").image(NetworkModule.IMAGE_BASE_URL + "orange-bell-pepper.jpg").build());
        vegetableItems.add(new ItemBuilder("Yellow Bell Pepper").image(NetworkModule.IMAGE_BASE_URL + "yellow-bell-pepper.jpg").build());
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
