package com.example.cctvnepal.model;

public class Customer {


    private String firstName;
    private String lastName;
    private String contactNumber;
    private String password;
    private String email;





    public Customer(String firstName, String lastName,String contactNumber, String password, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.contactNumber = contactNumber;
        this.email = email;
    }


    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstname(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
