package com.example.td_adviser.davinci;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Customer {

    private static final SimpleDateFormat BIRTHDATE_FORMAT = new SimpleDateFormat("YYYY-MM-dd");

    private String id;
    private String firstName, lastName;
    private int age;
    private boolean isMale;
    private Date birthDate;
    private double totalIncome;

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

}
