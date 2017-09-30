package com.handstandsam.shoppingapp.models;


public class ItemBuilder {

    Item item;

    public ItemBuilder(String label) {
        item = new Item(label, null, null);
    }

    public ItemBuilder label(String label) {
        item.setLabel(label);
        return this;
    }

    public Item build() {
        return item;
    }

    public ItemBuilder image(String image) {
        item.setImage(image);
        return this;
    }

    public ItemBuilder link(String link) {
        item.setLink(link);
        return this;
    }
}
