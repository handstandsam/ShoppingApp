package com.handstandsam.shoppingapp.models;


import java.io.Serializable;

public class Item implements Serializable {

    String label;

    String image;

    String link;

    public Item(String label) {
        this.label = label;
    }

    public String getImage() {
        return image;
    }

    public String getLabel() {
        return label;
    }
}
