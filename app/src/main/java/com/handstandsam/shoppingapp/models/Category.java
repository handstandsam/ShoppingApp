package com.handstandsam.shoppingapp.models;


import java.io.Serializable;

public class Category implements Serializable {

    String label;

    String image;

    String link;

    public Category() {

    }

    public String getLabel() {
        return label;
    }

    public String getImage() {
        return image;
    }
}
