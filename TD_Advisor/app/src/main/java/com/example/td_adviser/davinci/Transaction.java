package com.example.td_adviser.davinci;

import java.util.Vector;

public class Transaction {

    private String id;
    private String customerId, merchantId;
    private String description;
    private double amount;
    private Vector<String> categories;

    public Transaction(String id, String customerId, String merchantId, String description, double amount, Vector<String> categories) {
        this.id = id;
        this.customerId = customerId;
        this.merchantId = merchantId;
        this.description = description;
        this.amount = amount;
        this.categories = categories;
    }

    public String getId() {
        return id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public String getDescription() {
        return description;
    }

    public double getAmount() {
        return amount;
    }

    public Vector<String> getCategories() {
        return categories;
    }
}
