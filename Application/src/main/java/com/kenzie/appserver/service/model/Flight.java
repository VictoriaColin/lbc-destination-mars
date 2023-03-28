package com.kenzie.appserver.service.model;


public class Flight {

    private final String flightId;
    private final String flightName;
    private final String date;
    private final String departureLocation;
    private final String arrivalLocation;
    private final Integer totalSeatCapacity;
    private final Double ticketBasePrice;
    private final Boolean reservationClosed;

    public Flight(String flightId,
                  String flightName,
                  String date,
                  String departureLocation,
                  String arrivalLocation,
                  Integer totalSeatCapacity,
                  Double ticketBasePrice,
                  Boolean reservationClosed) {
        this.flightId = flightId;
        this.flightName = flightName;
        this.date = date;
        this.departureLocation = departureLocation;
        this.arrivalLocation = arrivalLocation;
        this.totalSeatCapacity = totalSeatCapacity;
        this.ticketBasePrice = ticketBasePrice;
        this.reservationClosed = reservationClosed;
    }

    public String getFlightId() {
        return flightId;
    }

    public String getFlightName() {
        return flightName;
    }

    public String getDate() {
        return date;
    }

    public String getDepartureLocation() {
        return departureLocation;
    }

    public String getArrivalLocation() {
        return arrivalLocation;
    }

    public Integer getTotalSeatCapacity() {
        return totalSeatCapacity;
    }

    public Double getTicketBasePrice() {
        return ticketBasePrice;
    }

    public Boolean getReservationClosed() {
        return reservationClosed;
    }
}
