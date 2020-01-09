package com.example.cctvnepal.model;

public class Feedback {

    private String customerEmail;
    private String customerName;
    private String title;
    private String feedback;

    public Feedback(String customerEmail,String customerName, String title, String feedback) {
        this.customerEmail = customerEmail;
        this.customerName = customerName;
        this.title = title;
        this.feedback = feedback;
    }

    public String getcustomerName() {
        return customerName;
    }

    public void setcustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
}
