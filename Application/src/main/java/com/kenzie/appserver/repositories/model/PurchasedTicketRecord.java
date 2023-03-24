package com.kenzie.appserver.repositories.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIndexHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import java.util.Objects;

@DynamoDBTable(tableName = "PurchasedTicket")
public class PurchasedTicketRecord {

    private String flightId;

    private String ticketId;

    private String dateOfPurchase;

    private Integer numberOfSeatsReserved;

    private Double pricePaid;

    /**
     * Most likely have to change the getFLightId() @DynamoDB annotation
     * I dont think we are able to use GSIs within the spring boot containers
     * Placing it in for now- can alter after discussions with coaches/Nathan/Peter
     */

//    @DynamoDBIndexHashKey(globalSecondaryIndexName = "FlightId", attributeName = "FlightId")
    public String getFlightId() {
        return flightId;
    }

    @DynamoDBHashKey(attributeName = "TicketId")
    public String getTicketId() {
        return ticketId;
    }

    @DynamoDBAttribute(attributeName = "DateOfPurchase")
    public String getDateOfPurchase() {
        return dateOfPurchase;
    }

    @DynamoDBAttribute(attributeName = "NumberOfSeatsReserved")
    public Integer getNumberOfSeatsReserved() {
        return numberOfSeatsReserved;
    }

    @DynamoDBAttribute(attributeName = "PricePaid")
    public Double getPricePaid() {
        return pricePaid;
    }

    public void setFlightId(String flightId) {
        this.flightId = flightId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public void setDateOfPurchase(String dateOfPurchase) {
        this.dateOfPurchase = dateOfPurchase;
    }

    public void setNumberOfSeatsReserved(Integer numberOfSeatsReserved) {
        this.numberOfSeatsReserved = numberOfSeatsReserved;
    }

    public void setPricePaid(Double pricePaid) {
        this.pricePaid = pricePaid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PurchasedTicketRecord that = (PurchasedTicketRecord) o;
        return ticketId.equals(that.ticketId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ticketId);
    }
}
