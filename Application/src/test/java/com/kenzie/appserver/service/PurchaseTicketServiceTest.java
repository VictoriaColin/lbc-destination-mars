package com.kenzie.appserver.service;

import com.kenzie.appserver.repositories.FlightRepository;
import com.kenzie.appserver.repositories.PurchasedTicketRepository;
import com.kenzie.appserver.repositories.model.PurchasedTicketRecord;
import com.kenzie.appserver.service.exceptions.ItemNotFoundException;
import com.kenzie.appserver.service.model.Flight;
import com.kenzie.appserver.service.FlightService;
import com.kenzie.appserver.service.model.PurchasedTicket;
import com.kenzie.appserver.service.model.ReservedTicket;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static java.util.UUID.randomUUID;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PurchaseTicketServiceTest {

    private PurchasedTicketRepository purchasedTicketRepository;

    private ReservedTicketService reservedTicketService;

    private PurchasedTicketService purchasedTicketService;

    private FlightService flightService;

    @BeforeEach
    void setup() {
        purchasedTicketRepository = mock(PurchasedTicketRepository.class);
        reservedTicketService = mock(ReservedTicketService.class);
        flightService = mock(FlightService.class);
        purchasedTicketService = new PurchasedTicketService(purchasedTicketRepository, reservedTicketService, flightService);

        //purchasedTicketService.setFlightService(flightService);

    }

    /**
     * ------------------------------------------------------------------------
     * purchasedTicketService.purchaseTicket
     * ------------------------------------------------------------------------
     **/

    @Test
    void purchased_Ticket() {

        String flightID = randomUUID().toString();
        String flightName = "MARS-FLIGHT";
        String ticketID = randomUUID().toString();
        String departureLocation = "FL";
        String arrivalLocation = "MARS";
        String dateofReservation = LocalDateTime.now().toString();
        Boolean reservationClosed = false;
        String dateofReservationClosed = null;
        Integer numberOfSeastsReserved = 10;
        Boolean isTickedPurchased = true;

        // mock for reserve ticket
        ReservedTicket reservedTicket = new ReservedTicket(flightID, flightName, ticketID, departureLocation, arrivalLocation, dateofReservation,
                reservationClosed, "dateofReservationClosed", numberOfSeastsReserved, false);

        ReservedTicket updatedTicket = new ReservedTicket(flightID, flightName, ticketID, departureLocation, arrivalLocation, dateofReservation,
                reservationClosed, dateofReservationClosed, numberOfSeastsReserved, isTickedPurchased);

        String date = LocalDateTime.now().plusDays(2).toString();
        Integer totalSeatCapacity = 10;
        Double ticketBasePrice = 100000.000;
        Flight flight = new Flight(flightID, flightName, date, departureLocation, arrivalLocation, totalSeatCapacity, ticketBasePrice, reservationClosed);

        when(reservedTicketService.findByReservedTicketId(ticketID)).thenReturn(reservedTicket);
        when(reservedTicketService.updateReserveTicket(updatedTicket)).thenReturn(updatedTicket);
        when(flightService.findByFlightId(flightID)).thenReturn(flight);

        PurchasedTicket purchasedTicket1 = purchasedTicketService.purchaseTicket(ticketID, numberOfSeastsReserved);

        Assertions.assertEquals(reservedTicket.getTicketId(), purchasedTicket1.getTicketId());

    }

    /**
     * ------------------------------------------------------------------------
     * purchasedTicketService.findbyFlightId
     * ------------------------------------------------------------------------
     **/

    @Test
    void findByFlightId() {

        String flightid = randomUUID().toString();

        PurchasedTicketRecord record = new PurchasedTicketRecord();
        record.setFlightId(flightid);
        record.setTicketId(randomUUID().toString());
        record.setDateOfPurchase("purchaseDate");
        record.setPricePaid(100000.000);


        when(purchasedTicketRepository.findByFlightId(flightid)).thenReturn(Arrays.asList(record));
        List<PurchasedTicket> purchasedTickets = purchasedTicketService.findByFlightId(flightid);


        Assertions.assertEquals(1, purchasedTickets.size(), "There is one Purchased Ticket");
        PurchasedTicket ticket = purchasedTickets.get(0);
        //Assertions.assertNull(ticket,"The Purchased Ticket is returned");
        Assertions.assertEquals(record.getFlightId(), ticket.getFlightId(), "The Flight Id matches");
        Assertions.assertEquals(record.getTicketId(), ticket.getTicketId(), "The Ticket id matches");
        Assertions.assertEquals(record.getDateOfPurchase(), ticket.getDateOfPurchase(), "The dates of purchase matches up");
        Assertions.assertEquals(record.getPricePaid(), ticket.getPricePaid(), "The price paid matches");

    }

    @Test
    public void createPurchaseTicket_failsReservation() throws Exception {
        //GIVEN
        String flightID = randomUUID().toString();
        String flightName = "MARS-FLIGHT";
        String ticketID = randomUUID().toString();
        String departureLocation = "FL";
        String arrivalLocation = "MARS";
        String dateofReservation = LocalDateTime.now().toString();
        Boolean reservationClosed = true;
        String dateofReservationClosed = null;
        Integer numberOfSeastsReserved = 10;
        Boolean isTickedPurchased = true;

        // mock for reserve ticket
        ReservedTicket reservedTicket = new ReservedTicket(flightID, flightName, ticketID, departureLocation, arrivalLocation, dateofReservation,
                reservationClosed, "dateofReservationClosed", numberOfSeastsReserved, false);

        ReservedTicket updatedTicket = new ReservedTicket(flightID, flightName, ticketID, departureLocation, arrivalLocation, dateofReservation,
                reservationClosed, dateofReservationClosed, numberOfSeastsReserved, isTickedPurchased);

        String date = LocalDateTime.now().plusDays(2).toString();
        Integer totalSeatCapacity = 10;
        Double ticketBasePrice = 100000.000;
        Flight flight = new Flight(flightID, flightName, date, departureLocation, arrivalLocation, totalSeatCapacity, ticketBasePrice, reservationClosed);

        when(reservedTicketService.findByReservedTicketId(ticketID)).thenReturn(reservedTicket);
        when(reservedTicketService.updateReserveTicket(updatedTicket)).thenReturn(updatedTicket);
        when(flightService.findByFlightId(flightID)).thenReturn(flight);


        ReservedTicket finalReservedTicket = reservedTicket;
        assertThrows(ResponseStatusException.class,
                ()-> purchasedTicketService.purchaseTicket(finalReservedTicket.getTicketId(), totalSeatCapacity));
    }



}