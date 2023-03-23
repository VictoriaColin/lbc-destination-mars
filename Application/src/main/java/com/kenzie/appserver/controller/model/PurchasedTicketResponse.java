package com.kenzie.appserver.controller.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PurchasedTicketResponse {

    @JsonProperty("ticketId")
    private String ticketId;

    @JsonProperty("dateOfPurchase")
    private String dateOfPurchase;

    @JsonProperty("numberOfSeatsReserved")
    private Integer numberOfSeatsReserved;

    @JsonProperty("pricePaid")
    private Double pricePaid;


    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public String getDateOfPurchase() {
        return dateOfPurchase;
    }

    public void setDateOfPurchase(String dateOfPurchase) {
        this.dateOfPurchase = dateOfPurchase;
    }

    public Integer getNumberOfSeatsReserved() {
        return numberOfSeatsReserved;
    }

    public void setNumberOfSeatsReserved(Integer numberOfSeatsReserved) {
        this.numberOfSeatsReserved = numberOfSeatsReserved;
    }

    public Double getPricePaid() {
        return pricePaid;
    }

    public void setPricePaid(Double pricePaid) {
        this.pricePaid = pricePaid;
    }
}
