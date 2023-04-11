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
     *
     *
     *     Looks like I did need to alter it, we'll see
     *     if this way is correct
     */
    @NotEmpty
    @JsonProperty("flightId")
    private String flightId;

    @NotEmpty
    @JsonProperty("flightName")
    private String flightName;

    @NotEmpty
    @JsonProperty("departureLocation")
    private String departureLocation;

    @NotEmpty
    @JsonProperty("arrivalLocation")
    private String arrivalLocation;

    @NotEmpty
    @JsonProperty("numberOfSeatsReserved")
    private Integer numberOfSeatsReserved;

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

    public String getDepartureLocation() {
        return departureLocation;
    }

    public void setDepartureLocation(String departureLocation) {
        this.departureLocation = departureLocation;
    }

    public String getArrivalLocation() {
        return arrivalLocation;
    }

    public void setArrivalLocation(String arrivalLocation) {
        this.arrivalLocation = arrivalLocation;
    }

    public Integer getNumberOfSeatsReserved() {
        return numberOfSeatsReserved;
    }

    public void setNumberOfSeatsReserved(Integer numberOfSeatsReserved) {
        this.numberOfSeatsReserved = numberOfSeatsReserved;
    }
}
