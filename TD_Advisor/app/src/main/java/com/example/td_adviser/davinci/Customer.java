package com.example.td_adviser.davinci;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public class Customer {

    private static final SimpleDateFormat BIRTHDATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    private String id;
    private String firstName, lastName;
    private int age;
    private boolean isMale;
    private Date birthDate;
    private double totalIncome;

    private Vector<BankAccount> bankAccounts = new Vector<BankAccount>();
    private Vector<Transaction> transactions = new Vector<Transaction>();

    private Map<String, Double> transactionCategoryCounts = new HashMap<>();

    public Customer(String id, String firstName, String lastName, int age, boolean isMale, String birthDate, double totalIncome) {
        Log.i("!!ID!!", id);
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
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -3);
        // In the future or older than 3 months: ignore
        if (transaction.getTime().after(new Date()) || transaction.getTime().before(calendar.getTime())) {
            return;
        }
        // Income transactions counting as credit card transactions with positive value, skip
        if (transaction.getCategories().contains("Income") || transaction.getType().startsWith("Deposit") || (transaction.getType().startsWith("Credit") && transaction.getAmount() < 0)) {
            return;
        }
        this.transactions.add(transaction);

        for (String category : transaction.getCategories()) {
            Log.i("!!TX!!", "!!TX!! " + category + " : " + transaction.getAmount());
            if (transactionCategoryCounts.containsKey(category)) {
                transactionCategoryCounts.put(category, transactionCategoryCounts.get(category) + transaction.getAmount());
            } else {
                transactionCategoryCounts.put(category, transaction.getAmount());
            }
        }
    }

    public Vector<Transaction> getTransactions() {
        return this.transactions;
    }

    public Map<String, Double> getTransactionCategoryCounts() {
        return this.transactionCategoryCounts;
    }

}
