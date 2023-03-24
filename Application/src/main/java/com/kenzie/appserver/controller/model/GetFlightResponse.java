package com.kenzie.appserver.controller.model;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class GetFlightResponse {
    @JsonProperty("id")
    private String id;

    @JsonProperty("flightName")
    private String flightName;

    @JsonProperty("date")
    private String date;

    @JsonProperty("departureLocation")
    private String departureLocation;

    @JsonProperty("arrivalLocation")
    private String arrivalLocation;

    @JsonProperty("numberOfSeatsReserved")
    private Integer numberOfSeatsReserved;

    @JsonProperty("ticketBasePrice")
    private Double ticketBasePrice;

    @JsonProperty("reservationClosed")
    private Boolean reservationClosed;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFlightName() {
        return flightName;
    }

    public void setFlightName(String flightName) {
        this.flightName = flightName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public Double getTicketBasePrice() {
        return ticketBasePrice;
    }

    public void setTicketBasePrice(Double ticketBasePrice) {
        this.ticketBasePrice = ticketBasePrice;
    }

    public Boolean getReservationClosed() {
        return reservationClosed;
    }

    public void setReservationClosed(Boolean reservationClosed) {
        this.reservationClosed = reservationClosed;
    }
}
