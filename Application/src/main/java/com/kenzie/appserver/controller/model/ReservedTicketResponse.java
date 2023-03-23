package com.kenzie.appserver.controller.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReservedTicketResponse {

    @JsonProperty("flightId")
    private String flightId;

    @JsonProperty("flightName")
    private String flightName;

    @JsonProperty("ticketId")
    private String ticketId;

    @JsonProperty("departureLocation")
    private String departureLocation;

    @JsonProperty("arrivalLocation")
    private String arrivalLocation;

    @JsonProperty("dateOfReservation")
    private String dateOfReservation;

    @JsonProperty("reservationClosed")
    private Boolean reservationClosed;

    @JsonProperty("dateOfReservationClosed")
    private String dateOfReservationClosed;

    @JsonProperty("numberOfSeatsReserved")
    private Integer numberOfSeatsReserved;

    @JsonProperty("purchasedTicket")
    private Boolean purchasedTicket;


    public String getFlightId() {
        return flightId;
    }

    public void setFlightId(String flightId) {
        this.flightId = flightId;
    }

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

    public String getDepartureCity() {
        return departureLocation;
    }

    public void setDepartureCity(String departureLocation) {
        this.departureLocation = departureLocation;
    }

    public String getArrivalCity() {
        return arrivalLocation;
    }

    public void setArrivalCity(String arrivalLocation) {
        this.arrivalLocation = arrivalLocation;
    }

    public String getDateOfReservation() {
        return dateOfReservation;
    }

    public void setDateOfReservation(String dateOfReservation) {
        this.dateOfReservation = dateOfReservation;
    }

    public Boolean getReservationClosed() {
        return reservationClosed;
    }

    public void setReservationClosed(Boolean reservationClosed) {
        this.reservationClosed = reservationClosed;
    }

    public String getDateOfReservationClosed() {
        return dateOfReservationClosed;
    }

    public void setDateOfReservationClosed(String dateOfReservationClosed) {
        this.dateOfReservationClosed = dateOfReservationClosed;
    }

    public Integer getNumberOfSeatsReserved() {
        return numberOfSeatsReserved;
    }

    public void setNumberOfSeatsReserved(Integer numberOfSeatsReserved) {
        this.numberOfSeatsReserved = numberOfSeatsReserved;
    }

    public Boolean getPurchasedTicket() {
        return purchasedTicket;
    }

    public void setPurchasedTicket(Boolean purchasedTicket) {
        this.purchasedTicket = purchasedTicket;
    }
}
