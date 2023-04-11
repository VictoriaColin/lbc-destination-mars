package com.kenzie.appserver.service.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ReservedTicket {

    private String flightId;

    private String flightName;

    private String ticketId;

    private String departureLocation;

    private String arrivalLocation;

    private String dateOfReservation;

    private Boolean reservationClosed;

    private String dateOfReservationClosed;

    private Integer numberOfSeatsReserved;

    private Boolean purchasedTicket;

    public ReservedTicket(String flightId,
                          String flightName,
                          String ticketId,
                          String departureLocation,
                          String arrivalLocation,
                          String dateOfReservation,
                          Boolean reservationClosed,
                          String dateOfReservationClosed,
                          Integer numberOfSeatsReserved,
                          Boolean purchasedTicket) {
        this.flightId = flightId;
        this.flightName = flightName;
        this.ticketId = ticketId;
        this.departureLocation = departureLocation;
        this.arrivalLocation = arrivalLocation;
        this.dateOfReservation = dateOfReservation;
        this.reservationClosed = reservationClosed;
        this.dateOfReservationClosed = dateOfReservationClosed;
        this.numberOfSeatsReserved = numberOfSeatsReserved;
        this.purchasedTicket = purchasedTicket;
    }

    public ReservedTicket(String flightId,
                          String flightName,
                          String ticketId,
                          String departureLocation,
                          String arrivalLocation,
                          String dateOfReservation,
                          Integer numberOfSeatsReserved) {
        this.flightId = flightId;
        this.flightName = flightName;
        this.ticketId = ticketId;
        this.departureLocation = departureLocation;
        this.arrivalLocation = arrivalLocation;
        this.dateOfReservation = dateOfReservation;
        this.reservationClosed = false;
        this.dateOfReservationClosed = null;
        this.numberOfSeatsReserved = numberOfSeatsReserved;
        this.purchasedTicket = false;
    }

    public String getFlightId() {
        return flightId;
    }

    public String getFlightName() {
        return flightName;
    }

    public String getTicketId() {
        return ticketId;
    }

    public String getDepartureLocation() {
        return departureLocation;
    }

    public String getArrivalLocation() {
        return arrivalLocation;
    }

    public String getDateOfReservation() {
        return dateOfReservation;
    }

    public Boolean getReservationClosed() {
        return reservationClosed;
    }

    public String getDateOfReservationClosed() {
        return dateOfReservationClosed;
    }

    public Integer getNumberOfSeatsReserved() {
        return numberOfSeatsReserved;
    }

    public Boolean getPurchasedTicket() {
        return purchasedTicket;
    }
}
