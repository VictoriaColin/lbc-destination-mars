package com.kenzie.appserver.controller.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotEmpty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SeatReservationResponse {
    @NotEmpty
    @JsonProperty("flightId")
    private String flightId;
    @NotEmpty
    @JsonProperty("seatNumber")
    private String seatNumber;
    @NotEmpty
    @JsonProperty("seatReservationClosed")
    private Boolean seatReservationClosed;
    @NotEmpty
    @JsonProperty("ticketId")
    private String ticketId;



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

    public Boolean getSeatReservationClosed() {
        return seatReservationClosed;
    }

    public void setSeatReservationClosed(Boolean seatReservationClosed) {
        this.seatReservationClosed = seatReservationClosed;
    }

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }
}
