package com.kenzie.appserver.repositories.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import java.util.Objects;

@DynamoDBTable(tableName = "Seat")
public class SeatRecord {

    private String flightId;
    private String seatNumber;

    private Boolean seatReservationClosed;

    private String ticketId;

    @DynamoDBHashKey(attributeName = "FlightId")
    public String getFlightId() {
        return flightId;
    }

    @DynamoDBAttribute(attributeName = "SeatNumber")
    public String getSeatNumber() {
        return seatNumber;
    }

    @DynamoDBAttribute(attributeName = "SeatReservationClosed")
    public Boolean getSeatReservationClosed() {
        return seatReservationClosed;
    }

    @DynamoDBAttribute(attributeName = "ticketId")
    public String getTicketId() {
        return ticketId;
    }


    public void setFlightId(String flightId) {
        this.flightId = flightId;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public void setSeatReservationClosed(Boolean seatReservationClosed) {
        this.seatReservationClosed = seatReservationClosed;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SeatRecord)) return false;
        SeatRecord that = (SeatRecord) o;
        return flightId.equals(that.flightId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(flightId);
    }
}
