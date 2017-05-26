package com.handstandsam.shoppingapp.mockaccount;


import com.handstandsam.shoppingapp.models.Category;
import com.handstandsam.shoppingapp.models.CategoryBuilder;
import com.handstandsam.shoppingapp.models.Item;
import com.handstandsam.shoppingapp.models.ItemBuilder;
import com.handstandsam.shoppingapp.models.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProduceMockAccount extends MockAccount {


    public static final String IMAGE_BASE_URL = "https://s3.amazonaws.com/shopping-app/produce/images/";

    User user;

    List<Category> categories = new ArrayList<>();

    public ProduceMockAccount() {
        user = new User("Sam", "Edwards");

        Category fruitCategory = new CategoryBuilder("Fruits").image(IMAGE_BASE_URL + "fuji-apple.jpg").build();
        List<Item> fruitItems = new ArrayList<>();
        fruitItems.add(new ItemBuilder("Granny Smith Apple").image(IMAGE_BASE_URL + "granny-smith-apple.jpg").build());
        fruitItems.add(new ItemBuilder("Gala Apple").image(IMAGE_BASE_URL + "gala-apple.jpg").build());
        fruitItems.add(new ItemBuilder("Pineapple").image(IMAGE_BASE_URL + "pineapple.jpg").build());
        fruitItems.add(new ItemBuilder("Red Delicious Apple").image(IMAGE_BASE_URL + "red-delicious-apple.jpg").build());
        fruitItems.add(new ItemBuilder("Fuji Apple").image(IMAGE_BASE_URL + "fuji-apple.jpg").build());
        fruitItems.add(new ItemBuilder("Lemon").image(IMAGE_BASE_URL + "lemons.jpg").build());
        fruitItems.add(new ItemBuilder("Grapefruit").image(IMAGE_BASE_URL + "grapefruit.jpg").build());
        fruitItems.add(new ItemBuilder("Lime").image(IMAGE_BASE_URL + "lime.jpg").build());
        fruitItems.add(new ItemBuilder("Orange").image(IMAGE_BASE_URL + "orange.jpg").build());
        itemByCategoryMap.put(fruitCategory.getLabel(), fruitItems);

        Category floralCategory = new CategoryBuilder("Floral").image(IMAGE_BASE_URL + "white-rose.jpg").build();
        List<Item> floralItems = new ArrayList<>();
        floralItems.add(new ItemBuilder("White Rose").image(IMAGE_BASE_URL + "white-rose.jpg").build());
        floralItems.add(new ItemBuilder("Sunflower").image(IMAGE_BASE_URL + "sunflower.jpg").build());
        itemByCategoryMap.put(floralCategory.getLabel(), floralItems);

        Category seafoodCategory = new CategoryBuilder("Seafood").image(IMAGE_BASE_URL + "shrimp.jpg").build();
        List<Item> seafoodItems = new ArrayList<>();
        seafoodItems.add(new ItemBuilder("Lobster Tail").image(IMAGE_BASE_URL + "lobster-tail.jpg").build());
        seafoodItems.add(new ItemBuilder("Shrimp").image(IMAGE_BASE_URL + "shrimp.jpg").build());
        seafoodItems.add(new ItemBuilder("Scallop").image(IMAGE_BASE_URL + "scallops.jpg").build());
        itemByCategoryMap.put(seafoodCategory.getLabel(), seafoodItems);

        Category vegetableCategory = new CategoryBuilder("Vegetables").image(IMAGE_BASE_URL + "kale.jpg").build();
        List<Item> vegetableItems = new ArrayList<>();
        vegetableItems.add(new ItemBuilder("Carrot").image(IMAGE_BASE_URL + "carrots.jpg").build());
        vegetableItems.add(new ItemBuilder("Cucumber").image(IMAGE_BASE_URL + "cucumber.jpg").build());
        vegetableItems.add(new ItemBuilder("Kale").image(IMAGE_BASE_URL + "kale.jpg").build());
        vegetableItems.add(new ItemBuilder("Romaine Lettuce").image(IMAGE_BASE_URL + "romaine-lettuce.jpg").build());
        vegetableItems.add(new ItemBuilder("Artichoke").image(IMAGE_BASE_URL + "artichoke.jpg").build());
        vegetableItems.add(new ItemBuilder("Beet").image(IMAGE_BASE_URL + "beet.jpg").build());
        vegetableItems.add(new ItemBuilder("Radish").image(IMAGE_BASE_URL + "radish.jpg").build());
        vegetableItems.add(new ItemBuilder("Tomato").image(IMAGE_BASE_URL + "tomato.jpg").build());
        vegetableItems.add(new ItemBuilder("Broccoli").image(IMAGE_BASE_URL + "broccoli.jpg").build());
        vegetableItems.add(new ItemBuilder("Avocado").image(IMAGE_BASE_URL + "avocado.jpg").build());
        vegetableItems.add(new ItemBuilder("Red Bell Pepper").image(IMAGE_BASE_URL + "red-bell-pepper.jpg").build());
        vegetableItems.add(new ItemBuilder("Orange Bell Pepper").image(IMAGE_BASE_URL + "orange-bell-pepper.jpg").build());
        vegetableItems.add(new ItemBuilder("Yellow Bell Pepper").image(IMAGE_BASE_URL + "yellow-bell-pepper.jpg").build());
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
