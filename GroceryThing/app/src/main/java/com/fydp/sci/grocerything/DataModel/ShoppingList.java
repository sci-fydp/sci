package com.fydp.sci.grocerything.DataModel;

public class ShoppingList {
    private String name;
    private int id;

    public ShoppingList(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }



    public void setName(String name) {
        this.name = name;
    }
}
