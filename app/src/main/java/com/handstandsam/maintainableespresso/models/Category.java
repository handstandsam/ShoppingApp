package com.handstandsam.maintainableespresso.models;


import java.io.Serializable;

public class Category implements Serializable {

    String label;

    String image;

    public Category() {

    }

    public String getLabel() {
        return label;
    }

    public String getImage() {
        return image;
    }
}
