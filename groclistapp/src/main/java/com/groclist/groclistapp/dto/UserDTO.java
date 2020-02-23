package com.groclist.groclistapp.dto;

import java.util.Date;

public class UserDTO {
    private String uname;
    private String firstName;
    private String lastName;
    private Date dob;
    private int adultsInFamily;
    private int kidsInFamily;

    public UserDTO(String uname, String firstName, String lastName, Date dob, int adultsInFamily, int kidsInFamily) {
        this.uname = uname;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;
        this.adultsInFamily = adultsInFamily;
        this.kidsInFamily = kidsInFamily;
    }

    public UserDTO(){}

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
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
}
