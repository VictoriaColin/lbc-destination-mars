package com.kenzie.appserver.service.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = "Seat")
public class Seat {

    private final String flightId;
    private final String seatNumber;

    private Boolean seatReservationClosed;

    private final String ticketId;

    public Seat(String flightId, String seatNumber, String ticketId, Boolean seatReservationClosed) {
        this.flightId = flightId;
        this.seatNumber = seatNumber;
        this.seatReservationClosed = seatReservationClosed;
        this.ticketId = ticketId;
    }

    public String getFlightId() {
        return flightId;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public Boolean getSeatReservationClosed() {
        return seatReservationClosed;
    }

    public String getTicketId() {
        return ticketId;
    }
}
