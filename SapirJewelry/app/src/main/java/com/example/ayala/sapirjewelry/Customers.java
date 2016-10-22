package com.example.ayala.sapirjewelry;

import java.io.Serializable;

/**
 * Created by ayala on 10/16/2016.
 */

public class Customers implements Serializable{

    protected String firstName;
    protected String lastName;
    protected String email;
    protected String phoneNumber;
    protected String birthday;
    protected String weddingDate;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getWeddingDate() {
        return weddingDate;
    }

    public void setWeddingDate(String weddingDate) {
        this.weddingDate = weddingDate;
    }

    @Override
    public String toString() {
        return "UserDbE " +
                     "[firstName=" + firstName + ", lastName=" + lastName + ", email=" + email +
                     ", phoneNumber=" + phoneNumber + ", birthday=" + birthday +
                     ", weddingDate=" + weddingDate
                + "]";
    }


}
