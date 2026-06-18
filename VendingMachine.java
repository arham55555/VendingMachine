/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.vendingmachine;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ARHAM
 */


public class VendingMachine {
    private String machineId;
    private String location;
    private List<Product> products;
    private double totalRevenue;
    private List<String> transactionLog;

    public VendingMachine(String machineId, String location) {
        this.machineId = machineId;
        this.location = location;
        this.products = new ArrayList<>();
        this.totalRevenue = 0.0;
        this.transactionLog = new ArrayList<>();
        loadDefaultProducts();
    }

    private void loadDefaultProducts() {
        products.add(new Product("Coca Cola",  1.50, 10, "Drinks"));
        products.add(new Product("Pepsi",      1.50, 10, "Drinks"));
        products.add(new Product("Water",      1.00,  8, "Drinks"));
        products.add(new Product("Lays Chips", 1.25,  7, "Snacks"));
        products.add(new Product("Oreos",      1.75,  6, "Snacks"));
        products.add(new Product("Snickers",   2.00,  5, "Candy"));
        products.add(new Product("KitKat",     1.75,  5, "Candy"));
        products.add(new Product("Gum",        0.75, 12, "Candy"));
    }

    public double purchaseProduct(int index, double amountPaid) {
        if (index < 0 || index >= products.size()) return -1;
        Product p = products.get(index);
        if (!p.isAvailable())          return -2;
        if (amountPaid < p.getPrice()) return -3;
        p.dispense();
        double change = amountPaid - p.getPrice();
        totalRevenue += p.getPrice();
        transactionLog.add("SOLD: " + p.getName() +
            " | Paid: $" + String.format("%.2f", amountPaid) +
            " | Change: $" + String.format("%.2f", change));
        return change;
    }

    public void addProduct(Product p)              { products.add(p); }
    public void removeProduct(int index)           { if (index >= 0 && index < products.size()) products.remove(index); }
    public void restockProduct(int index, int qty) { if (index >= 0 && index < products.size()) products.get(index).restock(qty); }

    public List<Product> getProducts()      { return products; }
    public double getTotalRevenue()          { return totalRevenue; }
    public List<String> getTransactionLog() { return transactionLog; }
    public String getMachineId()             { return machineId; }
    public String getLocation()              { return location; }

    public Object[][] getProductTableData() {
        Object[][] data = new Object[products.size()][5];
        for (int i = 0; i < products.size(); i++) {
            Product p = products.get(i);
            data[i][0] = i + 1;
            data[i][1] = p.getName();
            data[i][2] = p.getCategory();
            data[i][3] = String.format("$%.2f", p.getPrice());
            data[i][4] = p.getQuantity();
        }
        return data;
    }
}
