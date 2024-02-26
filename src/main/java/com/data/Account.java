package com.data;

import java.sql.Date;

/**
 * Account Class for Data and its Details
 * @author Dhanya Umapathi (Expleo)
 * @since 20 FEB 2024
 */
public class Account extends Person{
    private String id;
    private String email;

    public Account() {} 

    public Account(String accountID, String email, String firstName, String lastName, char gender, long phone,
            Date dob, String address) {
        super(firstName, lastName, gender, phone, dob, address);
        this.id = accountID;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String accountID) {
        this.id = accountID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
