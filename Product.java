/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.vendingmachine;

/**
 *
 * @author ARHAM
 */


public class Product {
    private String name;
    private double price;
    private int quantity;
    private String category;

    public Product(String name, double price, int quantity, String category) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
    }

    public String getName()      { return name; }
    public double getPrice()     { return price; }
    public int getQuantity()     { return quantity; }
    public String getCategory()  { return category; }

    public void setName(String name)          { this.name = name; }
    public void setPrice(double price)        { this.price = price; }
    public void setQuantity(int quantity)     { this.quantity = quantity; }
    public void setCategory(String category)  { this.category = category; }

    public boolean isAvailable() { return quantity > 0; }

    public boolean dispense() {
        if (isAvailable()) { quantity--; return true; }
        return false;
    }

    public void restock(int amount) { this.quantity += amount; }
}
