package com.kenzie.appserver.controller.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

public class FlightUpdateRequest {
    @NotEmpty
    @JsonProperty("flightId")
    private String flightId;

    @NotEmpty
    @JsonProperty("flightName")
    private String flightName;

    @JsonFormat(shape =  JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonProperty("date")
    private String date;

    @NotEmpty
    @JsonProperty("departureLocation")
    private String departureLocation;

    @NotEmpty
    @JsonProperty("arrivalLocation")
    private String arrivalLocation;

    @Min(0)
    @JsonProperty("totalSeatCapacity")
    private Integer totalSeatCapacity;

    @Min(0)
    @JsonProperty("ticketBasePrice")
    private Double ticketBasePrice;

    @JsonProperty("reservationClosed")
    private Boolean reservationClosed;


    public String getFlightId() {
        return flightId;
    }

    public void setFlightId(String flightId) {
        this.flightId = flightId;
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



    public void setFlightName(String flightName) {
        this.flightName = flightName;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setDepartureLocation(String departureLocation) {
        this.departureLocation = departureLocation;
    }

    public void setArrivalLocation(String arrivalLocation) {
        this.arrivalLocation = arrivalLocation;
    }

    public void setTotalSeatCapacity(Integer totalSeatCapacity) {
        this.totalSeatCapacity = totalSeatCapacity;
    }

    public void setTicketBasePrice(Double ticketBasePrice) {
        this.ticketBasePrice = ticketBasePrice;
    }

    public void setReservationClosed(Boolean reservationClosed) {
        this.reservationClosed = reservationClosed;
    }

}
