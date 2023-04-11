package com.kenzie.appserver.service.model;


public class PurchasedTicket {

    private String flightId;

    private String ticketId;

    private String dateOfPurchase;

    private Integer numberOfSeatsReserved;

    private Double pricePaid;

    public PurchasedTicket(String flightId,
                           String ticketId,
                           String dateOfPurchase,
                           Integer numberOfSeatsReserved,
                           Double pricePaid) {
        this.flightId = flightId;
        this.ticketId = ticketId;
        this.dateOfPurchase = dateOfPurchase;
        this.numberOfSeatsReserved = numberOfSeatsReserved;
        this.pricePaid = pricePaid;
    }

    public String getFlightId() {
        return flightId;
    }

    public String getTicketId() {
        return ticketId;
    }

    public String getDateOfPurchase() {
        return dateOfPurchase;
    }

    public Integer getNumberOfSeatsReserved() {
        return numberOfSeatsReserved;
    }

    public Double getPricePaid() {
        return pricePaid;
    }
}
