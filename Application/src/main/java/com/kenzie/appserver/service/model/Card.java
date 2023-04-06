package com.kenzie.appserver.service.model;

public class Card {

    private String cardNumber;
    private String customerName;
    private String expirationDate;
    private String cvvNumber;

    public Card(String cardNumber, String customerName, String expirationDate, String cvvNumber) {
        this.cardNumber = cardNumber;
        this.customerName = customerName;
        this.expirationDate = expirationDate;
        this.cvvNumber = cvvNumber;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getCvvNumber() {
        return cvvNumber;
    }

    public void setCvvNumber(String cvvNumber) {
        this.cvvNumber = cvvNumber;
    }
}
