package com.kenzie.appserver.controller.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotEmpty;

public class CardValidationRequest {

    @NotEmpty
    @JsonProperty("cardNumber")
    private String cardNumber;

    @NotEmpty
    @JsonProperty("customerName")
    private String customerName;

    @NotEmpty
    @JsonProperty("expirationDate")
    private String expirationDate;

    @NotEmpty
    @JsonProperty("cvvNumber")
    private String cvvNumber;

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
