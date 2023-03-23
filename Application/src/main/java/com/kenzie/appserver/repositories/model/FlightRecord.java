package com.kenzie.appserver.repositories.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import java.util.Objects;

@DynamoDBTable(tableName = "Flight")
public class FlightRecord {

    private String id;
    private String name;
    private String date;
    private String departureLocation;
    private String arrivalLocation;
    private Integer totalSeatCapacity;
    private Double ticketBasePrice;
    private Boolean reservationClosed;

    @DynamoDBHashKey(attributeName = "Id")
    public String getId() {
        return id;
    }

    @DynamoDBAttribute(attributeName = "Name")
    public String getName() {
        return name;
    }

    @DynamoDBAttribute(attributeName = "Date")
    public String getDate() {
        return date;
    }

    @DynamoDBAttribute(attributeName = "DepartureLocation")
    public String getDepartureLocation() {
        return departureLocation;
    }

    @DynamoDBAttribute(attributeName = "ArrivalLocation")
    public String getArrivalLocation() {
        return arrivalLocation;
    }

    @DynamoDBAttribute(attributeName = "TotalSeatCapacity")
    public Integer getTotalSeatCapacity() {
        return totalSeatCapacity;
    }

    @DynamoDBAttribute(attributeName = "TicketBasePrice")
    public Double getTicketBasePrice() {
        return ticketBasePrice;
    }

    @DynamoDBAttribute(attributeName = "ReservationClosed")
    public Boolean getReservationClosed() {
        return reservationClosed;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FlightRecord that = (FlightRecord) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
