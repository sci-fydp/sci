package com.fydp.sci.grocerything.DataModel;


public class Grocery {

    private String name;
    private int id;
    private String desc;

    public Grocery()
    {
        id = GroceryConstants.ERROR_ID_NUMBER;
        name = "I AM ERROR";
        desc = "";
    }
    public Grocery(Grocery g)
    {
        this.name = g.name;
        this.id = g.id;
        this.desc = g.desc;
    }

    public Grocery (String name, int id, String desc)
    {
        this.name = name;
        this.id = id;
        this.desc = desc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription()
    {
        return desc;
    }

    @Override
    public String toString()
    {
        return name;
    }


    @Override
    public boolean equals(Object o)
    {
        if (o == this)
            return true;
        if (o instanceof Grocery)
        {
            Grocery g = (Grocery) o;
            if (g.id == id && g.name.equals(this.name) && g.desc.equals(this.desc))
                return true;
        }
        return false;
    }

    @Override
    public int hashCode()
    {
        return name.hashCode() + desc.hashCode() + id;
    }
}
