package com.handstandsam.maintainableespresso.models;


import java.io.Serializable;

public class Item implements Serializable {

    String label;
    public String image;

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
