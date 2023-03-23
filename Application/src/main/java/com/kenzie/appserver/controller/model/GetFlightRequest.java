package com.kenzie.appserver.controller.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

public class GetFlightRequest {
    @NotEmpty
    @JsonProperty("departureLocation")
    private String departureLocation;

    @NotEmpty
    @JsonProperty("arrivalLocation")
    private String arrivalLocation;

    @JsonFormat(shape =  JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonProperty("date")
    private LocalDate date;

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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
