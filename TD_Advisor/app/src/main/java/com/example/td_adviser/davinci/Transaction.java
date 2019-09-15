package com.example.td_adviser.davinci;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

public class Transaction {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    private String id;
    private String customerId, merchantId;
    private String description;
    private Date time = new Date();
    private double amount;
    private String type;
    private Vector<String> categories;

    public Transaction(String id, String customerId, String merchantId, String description, String time, double amount, String type, Vector<String> categories) {
        this.id = id;
        this.customerId = customerId;
        this.merchantId = merchantId;
        this.description = description;
        try {
            this.time = DATE_FORMAT.parse(time.split("T")[0]);
        } catch (Exception e) {
        }
        this.amount = amount;
        this.type = type;
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

    public Date getTime() {
        return time;
    }

    public String getType() {
        return type;
    }
}
