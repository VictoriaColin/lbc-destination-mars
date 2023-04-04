package com.kenzie.appserver.controller.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PurchasedTicketResponse {

    //added in flight name while creating PurchasedTicketController
    @JsonProperty("flightName")
    private String flightName;
    @JsonProperty("ticketId")
    private String ticketId;

    @JsonProperty("dateOfPurchase")
    private String dateOfPurchase;

    @JsonProperty("numberOfSeatsReserved")
    private Integer numberOfSeatsReserved;

    @JsonProperty("pricePaid")
    private Double pricePaid;

    // credit card validation failed or successful
    @JsonProperty("creditCardValid")
    private Boolean creditCardValid;
    //private boolean cardValid;



    public String getFlightName() {
        return flightName;
    }

    public void setFlightName(String flightName) {
        this.flightName = flightName;
    }

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

    public Boolean getCreditCardValid() {
        return creditCardValid;
    }

    public void setCreditCardValid(Boolean creditCardValid) {
        this.creditCardValid = creditCardValid;
    }
}
