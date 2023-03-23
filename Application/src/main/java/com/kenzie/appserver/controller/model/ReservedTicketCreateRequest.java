package com.kenzie.appserver.controller.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotEmpty;

public class ReservedTicketCreateRequest {


    /**
     * Not sure if flightId will suffice, it should,
     * if not:
     *     private String departureLocation;
     *
     *     private String arrivalLocation;
     *
     *     private String departureDate;
     *
     *     private String returnDate;
     */
    @NotEmpty
    @JsonProperty("flightId")
    private String flightId;

    public String getFlightId() {
        return flightId;
    }

    public void setFlightId(String flightId) {
        this.flightId = flightId;
    }
}
