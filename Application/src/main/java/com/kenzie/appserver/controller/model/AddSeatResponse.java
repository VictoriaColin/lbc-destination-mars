package com.kenzie.appserver.controller.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotEmpty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AddSeatResponse {

    @NotEmpty
    @JsonProperty("flightName")
    private String flightName;

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
    @JsonProperty("seatReservationResponse")
    private Boolean seatReservationResponse;

    public String getFlightName() {
        return flightName;
    }

    public void setFlightName(String flightName) {
        this.flightName = flightName;
    }

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

    public Boolean isSeatReservationResponse() {
        return seatReservationResponse;
    }

    public void setSeatReservationResponse(Boolean seatReservationResponse) {
        this.seatReservationResponse = seatReservationResponse;
    }
}
