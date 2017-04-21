package com.handstandsam.maintainableespresso.models;


public class ItemBuilder {

    Item item;

    public ItemBuilder() {
        item = new Item();
    }

    public ItemBuilder label(String label) {
        item.label = label;
        return this;
    }

    public Item build() {
        return item;
    }
}
