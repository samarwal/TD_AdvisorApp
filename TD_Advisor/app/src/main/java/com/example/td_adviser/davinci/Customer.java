package com.example.td_adviser.davinci;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Vector;

public class Customer {

    private static final SimpleDateFormat BIRTHDATE_FORMAT = new SimpleDateFormat("YYYY-MM-dd");

    private String id;
    private String firstName, lastName;
    private int age;
    private boolean isMale;
    private Date birthDate;
    private double totalIncome;

    private Vector<BankAccount> bankAccounts = new Vector<BankAccount>();
    private Vector<Transaction> transactions = new Vector<Transaction>();

    public Customer(String id, String firstName, String lastName, int age, boolean isMale, String birthDate, double totalIncome) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.isMale = isMale;
        try {
            this.birthDate = BIRTHDATE_FORMAT.parse(birthDate);
        } catch (final Exception e) {
            this.birthDate = new Date(0L);
        }
        this.totalIncome = totalIncome;
    }

    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getAge() {
        return age;
    }

    public boolean isMale() {
        return isMale;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public double getTotalIncome() {
        return totalIncome;
    }

    public void addBankAccount(BankAccount account) {
        this.bankAccounts.add(account);
    }

    public Vector<BankAccount> getBankAccounts() {
        return this.bankAccounts;
    }

    public double getTotalBankAccountBalance() {
        double balance = 0;
        for (BankAccount account : bankAccounts) {
            balance += account.getBalance();
        }
        return balance;
    }

    public void addTransaction(Transaction transaction) {
        this.transactions.add(transaction);
    }

    public Vector<Transaction> getTransactions() {
        return this.transactions;
    }

}
