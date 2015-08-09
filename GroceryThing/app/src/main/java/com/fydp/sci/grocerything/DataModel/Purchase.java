package com.fydp.sci.grocerything.DataModel;


public class Purchase {
    private double price;
    private Grocery grocery;
    private int locationId; //TODO

    public Purchase (Grocery grocery)
    {
        this.grocery = new Grocery(grocery);
        this.price = 0;
        locationId = 0;
    }

    public Purchase (Grocery grocery, double price)
    {
        this.grocery = new Grocery(grocery);
        this.price = price;
        locationId = 0;
    }


    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public Grocery getGrocery() {
        return grocery;
    }

    public void setPrice(double p)
    {
        price = p;
    }

    public double getPrice() {
        return price;
    }

    public String getName()
    {
        return grocery.getName();
    }

    public int getId() {
        return grocery.getId();
    }

    public String getDescription(){ return grocery.getDescription(); };
}
