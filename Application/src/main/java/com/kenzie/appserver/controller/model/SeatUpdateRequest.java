package com.kenzie.appserver.controller.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotEmpty;

public class SeatUpdateRequest {
    @NotEmpty
    @JsonProperty("flightId")
    private String flightId;

    @NotEmpty
    @JsonProperty("seatNumber")
    private String seatNumber;

    @NotEmpty
    @JsonProperty("ticketId")
    private String ticketId;

    @NotEmpty
    @JsonProperty("seatReservationClosed")
    private Boolean seatReservationClosed;

    public String getFlightId() {
        return flightId;
    }

    public void setFlightId(String flightId) {
        this.flightId = flightId;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public Boolean getSeatReservationClosed() {
        return seatReservationClosed;
    }

    public void setSeatReservationClosed(Boolean seatReservationClosed) {
        this.seatReservationClosed = seatReservationClosed;
    }
}
