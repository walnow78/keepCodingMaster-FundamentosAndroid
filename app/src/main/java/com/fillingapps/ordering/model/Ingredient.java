package com.fillingapps.ordering.model;

public class Ingredient {

    private String mName;

    public Ingredient(String name) {
        mName = name;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }
}