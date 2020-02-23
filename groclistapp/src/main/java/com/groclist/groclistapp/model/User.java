package com.groclist.groclistapp.model;

import java.util.Date;

public class User {
    private String uname;
    private String password;
    private String firstName;
    private String lastName;
    private Date dob;
    private int adultsInFamily;
    private int kidsInFamily;
    private String emailAddress;

    public User(String uname, String password, String firstName, String lastName, Date dob, int adultsInFamily,
                int kidsInFamily, String emailAddress) {
        this.uname = uname;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;
        this.adultsInFamily = adultsInFamily;
        this.kidsInFamily = kidsInFamily;
        this.emailAddress = emailAddress;
    }

    public User(){}

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public int getAdultsInFamily() {
        return adultsInFamily;
    }

    public void setAdultsInFamily(int adultsInFamily) {
        this.adultsInFamily = adultsInFamily;
    }

    public int getKidsInFamily() {
        return kidsInFamily;
    }

    public void setKidsInFamily(int kidsInFamily) {
        this.kidsInFamily = kidsInFamily;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }
}
