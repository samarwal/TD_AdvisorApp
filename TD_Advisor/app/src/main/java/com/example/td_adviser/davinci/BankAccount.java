package com.example.td_adviser.davinci;

public class BankAccount {

    private String id;
    private boolean isSavings;
    private double balance;
    private String branchNumber;

    public BankAccount(String id, boolean isSavings, double balance, String branchNumber) {
        this.id = id;
        this.isSavings = isSavings;
        this.balance = balance;
        this.branchNumber = branchNumber;
    }

    public String getId() {
        return id;
    }

    public double getBalance() {
        return balance;
    }

    public String getBranchNumber() {
        return branchNumber;
    }

    public boolean isSavings() {
        return isSavings;
    }

}
