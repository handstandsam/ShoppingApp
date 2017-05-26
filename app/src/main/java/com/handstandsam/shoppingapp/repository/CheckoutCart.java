package com.handstandsam.shoppingapp.repository;

import com.handstandsam.shoppingapp.models.Item;

import java.util.ArrayList;
import java.util.List;

public class CheckoutCart {

    List<Item> itemsInCart = new ArrayList<>();

    public CheckoutCart() {

    }

    public void empty() {
        itemsInCart.clear();
    }

    public void addItem(Item item) {
        itemsInCart.add(item);
    }

    public void removeItem(Item item) {
        itemsInCart.remove(item);
    }

    public List<Item> getItems() {
        return itemsInCart;
    }
}
