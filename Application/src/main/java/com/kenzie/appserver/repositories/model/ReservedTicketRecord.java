package com.kenzie.appserver.repositories.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import java.util.Objects;

@DynamoDBTable(tableName = "ReservedTicket")
public class ReservedTicketRecord {

    private String flightId;

    private String flightName;

    private String ticketId;

    private String departureLocation;

    private String arrivalLocation;

    private String dateOfReservation;

    private Boolean reservationClosed;

    private String dateOfReservationClosed;

    private Integer numberOfSeatsReserved;

    private Boolean purchasedTicket;

    /**@DynamoDB annotation in question for flightId and flightName
     * just an attribute?
     * unnecessary?
     * Discussion needed to search options
     * Possibility of added helper service methods
     * to take care of the need to "query"
     * since it looks like we cant use DynamoDB tables on AWS
     *
     */
    public String getFlightId() {
        return flightId;
    }

    public String getFlightName() {
        return flightName;
    }

    @DynamoDBHashKey(attributeName = "TicketId")
    public String getTicketId() {
        return ticketId;
    }

    @DynamoDBAttribute(attributeName = "DepartureLocation")
    public String getDepartureLocation() {
        return departureLocation;
    }

    @DynamoDBAttribute(attributeName = "ArrivalLocation")
    public String getArrivalLocation() {
        return arrivalLocation;
    }

    @DynamoDBAttribute(attributeName = "DateOfReservation")
    public String getDateOfReservation() {
        return dateOfReservation;
    }

    @DynamoDBAttribute(attributeName = "ReservationClosed")
    public Boolean getReservationClosed() {
        return reservationClosed;
    }

    @DynamoDBAttribute(attributeName = "DateReservationClosed")
    public String getDateOfReservationClosed() {
        return dateOfReservationClosed;
    }

    @DynamoDBAttribute(attributeName = "NumberOfSeatsReserved")
    public Integer getNumberOfSeatsReserved() {
        return numberOfSeatsReserved;
    }

    @DynamoDBAttribute(attributeName = "PurchasedTicket")
    public Boolean getPurchasedTicket() {
        return purchasedTicket;
    }

    public void setFlightId(String flightId) {
        this.flightId = flightId;
    }

    public void setFlightName(String flightName) {
        this.flightName = flightName;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public void setDepartureLocation(String departureLocation) {
        this.departureLocation = departureLocation;
    }

    public void setArrivalLocation(String arrivalLocation) {
        this.arrivalLocation = arrivalLocation;
    }

    public void setDateOfReservation(String dateOfReservation) {
        this.dateOfReservation = dateOfReservation;
    }

    public void setReservationClosed(Boolean reservationClosed) {
        this.reservationClosed = reservationClosed;
    }

    public void setDateOfReservationClosed(String dateOfReservationClosed) {
        this.dateOfReservationClosed = dateOfReservationClosed;
    }

    public void setNumberOfSeatsReserved(Integer numberOfSeatsReserved) {
        this.numberOfSeatsReserved = numberOfSeatsReserved;
    }

    public void setPurchasedTicket(Boolean purchasedTicket) {
        this.purchasedTicket = purchasedTicket;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReservedTicketRecord that = (ReservedTicketRecord) o;
        return ticketId.equals(that.ticketId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ticketId);
    }
}
