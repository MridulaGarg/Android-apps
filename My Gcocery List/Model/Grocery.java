package com.example.grocery.grocerylist.Model;

public class Grocery {

    private String name;
    private String quantity;
    private String dateItemadded;
    private int id;

    public Grocery() {
    }

    public Grocery(String name, String quantity, String dateItemadded, int id) {
        this.name = name;
        this.quantity = quantity;
        this.dateItemadded = dateItemadded;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getDateItemadded() {
        return dateItemadded;
    }

    public void setDateItemadded(String dateItemadded) {
        this.dateItemadded = dateItemadded;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}