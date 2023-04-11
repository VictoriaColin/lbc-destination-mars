package com.kenzie.appserver.controller.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

public class PurchasedTicketCreateRequest {

    @NotEmpty
    @JsonProperty("ticketId")
    private String ticketId;

    @Min(0)
    @JsonProperty("numberOfSeatsReserved")
    private Integer numberOfSeatsReserved;

    // Customer name
    @JsonProperty("customerName")
    private String customerName;

    // credit card number as a String
    @JsonProperty("creditCard")
    private String creditCard;

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public Integer getNumberOfSeatsReserved() {
        return numberOfSeatsReserved;
    }

    public void setNumberOfSeatsReserved(Integer numberOfSeatsReserved) {
        this.numberOfSeatsReserved = numberOfSeatsReserved;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(String creditCard) {
        this.creditCard = creditCard;
    }
}
