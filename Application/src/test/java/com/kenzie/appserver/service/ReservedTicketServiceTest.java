package com.kenzie.appserver.service;

import com.kenzie.appserver.repositories.ReservedTicketRepository;
import com.kenzie.appserver.repositories.model.ReservedTicketRecord;
import com.kenzie.appserver.service.model.Flight;
import com.kenzie.appserver.service.model.ReservedTicket;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.web.server.ResponseStatusException;
import org.testcontainers.shaded.com.google.common.base.Verify;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentLinkedQueue;

import static java.util.UUID.randomUUID;
import static org.mockito.Mockito.*;

public class ReservedTicketServiceTest {


    private FlightService flightService;
    private ReservedTicketRepository reservedTicketRepository;
    private ConcurrentLinkedQueue<ReservedTicket> reservedTicketsQueue;
    private ReservedTicketService reservedTicketService;

    @BeforeEach
    void setup() {
        // this has to follow the same naming converntioins as actual constructor being tested.
        reservedTicketRepository = mock(ReservedTicketRepository.class);
        flightService = mock(FlightService.class);
        reservedTicketsQueue = new ConcurrentLinkedQueue<>();
        reservedTicketService = new ReservedTicketService(flightService, reservedTicketRepository, reservedTicketsQueue);


    }

    /**
     * ------------------------------------------------------------------------
     * reservedTicketService.findAllReservationTickets
     * ------------------------------------------------------------------------
     **/

    @Test
    void findAllReservations() {


        //Given
        ReservedTicketRecord record1 = new ReservedTicketRecord();
        record1.setFlightId("fligthId1");
        record1.setFlightName("flightName1");
        record1.setTicketId("ticketId1");
        record1.setDepartureLocation("departureLocation1");
        record1.setArrivalLocation("ArrivalLocation1");
        record1.setDateOfReservation("dateofReservation1");
        record1.setReservationClosed(false);
        record1.setDateOfReservationClosed("dateofReservationClosed");
        record1.setNumberOfSeatsReserved(3);
        record1.setPurchasedTicket(true);

        ReservedTicketRecord record2 = new ReservedTicketRecord();
        record2.setFlightId("fligthId2");
        record2.setFlightName("flightName");
        record2.setTicketId("ticketId");
        record2.setDepartureLocation("departureLocation");
        record2.setArrivalLocation("ArrivalLocation");
        record2.setDateOfReservation("dateofReservation");
        record2.setReservationClosed(false);
        record2.setDateOfReservationClosed("dateofReservationClosed");
        record2.setNumberOfSeatsReserved(3);
        record2.setPurchasedTicket(true);


        List<ReservedTicketRecord> records = new ArrayList<>();
        records.add(record1);
        records.add(record2);

        // when reserveticket repositoty i find all
        when(reservedTicketRepository.findAll()).thenReturn(records);

        //when
        List<ReservedTicket> result = reservedTicketService.findAllReservationTickets();

        //then
        Assertions.assertNotNull(result, "result should not be Null !");
        Assertions.assertEquals(2, result.size(), "Two Tickets have been reservede :)");


        for (ReservedTicket ticket : result) {
            if (Objects.equals(ticket.getTicketId(), record1.getTicketId())) {
                Assertions.assertEquals(record1.getFlightId(), ticket.getFlightId(), "The flight match up");
                Assertions.assertEquals(record1.getFlightName(), ticket.getFlightName(), "The flight names match");
            } else if (Objects.equals(ticket.getTicketId(), record2.getTicketId())) {
                Assertions.assertEquals(record2.getFlightId(), ticket.getFlightId(), "The flight match up");
                Assertions.assertEquals(record2.getFlightName(), ticket.getFlightName(), "The flight names match");

            } else {
                Assertions.assertTrue(false, "Reserved Ticket returned that was not in the records!");
            }


        }
    }

    /**
     * ------------------------------------------------------------------------
     * reservedTicketService.findAllUnclosedReservationTickets
     * ------------------------------------------------------------------------
     **/
    @Test
    void reserve_ticket() {
        String flightId = randomUUID().toString();
        String flightName = "name";
        String ticketId = randomUUID().toString();
        String departureLocation = "FL";
        String arrivalLocation = "MARS";
        String dateOfReservation = LocalDateTime.now().toString();
        Boolean reservationClosed = false;
        // why dateOfReservationClosed is null instead of LocalDateTime.now().toString()
        String dateOfReservationClosed = null;
        Integer numberOfSeatsReserved = 2;
        Boolean purchasedTicket = true;
        String date = LocalDateTime.now().toString();
        Integer totalSeatCapacity = 10;
        Double ticketBasePrice = 0.0;

        Flight flight = new Flight(flightId, flightName, date, departureLocation, arrivalLocation, totalSeatCapacity, ticketBasePrice, reservationClosed);
        when(flightService.findByFlightId(flightId)).thenReturn(flight);

        ReservedTicket reservedTicket = new ReservedTicket(flightId, flightName, ticketId, departureLocation, arrivalLocation, dateOfReservation, reservationClosed, dateOfReservationClosed, numberOfSeatsReserved, purchasedTicket);
        ReservedTicket result = reservedTicketService.reservedTicket(reservedTicket);

        Assertions.assertTrue(reservedTicketsQueue.contains(result));
    }


    /**
     * ------------------------------------------------------------------------
     * reservedTicketService.reserveTicket
     * ------------------------------------------------------------------------
     **/

    @Test
    void reservedTicket_empty_ticket_throwsError() {
        String flightId = randomUUID().toString();
        String flightName = "MarsTicket";
        String ticketId = randomUUID().toString();
        String departureLocation = "FL";
        String arrivalLocation = "MARS";
        String dateOfReservation = LocalDateTime.now().toString();
        Boolean reservationClosed = false;
        String dateOfReservationClosed = LocalDateTime.now().toString();
        Boolean purchasedTicket = false;
        Integer numberOfSeatsReserved = null;
        ReservedTicket reservedTicket = new ReservedTicket("", "", "", "", "", "", false, "", 0, false);
        when(flightService.findByFlightId("")).thenReturn(null);

        Assertions.assertThrows(ResponseStatusException.class,
                () -> reservedTicketService.reservedTicket(reservedTicket));
    }

    @Test
    void reservedTicket_returns_ticket() {

        String flightid = randomUUID().toString();
        String flightName = "MarsFlight";
        String date = LocalDateTime.now().toString();
        String departureLocation = "FL";
        String arrivalLocation = "MARS";
        Integer totalSeatCapacity = 10;
        int ticketBasePrice = 100000;
        Boolean reservationClosed = false;
        Flight flight = new Flight(flightid, flightName, date, departureLocation, arrivalLocation, totalSeatCapacity, (double) ticketBasePrice, reservationClosed);
        Boolean purchasedTicket = true;
        String dateOfReservation = LocalDateTime.now().toString();
        Integer numberOfSeatsReserved = 10;
        String ticketId = randomUUID().toString();

        String dateOfReservationClosed = LocalDateTime.now().toString();

        ReservedTicket reservedTicket = new ReservedTicket(flightid, flightName, ticketId, departureLocation, arrivalLocation, dateOfReservation, reservationClosed, dateOfReservationClosed, numberOfSeatsReserved, purchasedTicket);
        when(flightService.findByFlightId(flightid)).thenReturn(flight);

        ReservedTicket reservedTicket1 = reservedTicketService.reservedTicket(reservedTicket);
        Assertions.assertNotNull(reservedTicket1);
        Assertions.assertEquals(reservedTicket, reservedTicket1);

    }

    /**
     * ------------------------------------------------------------------------
     * reservedTicketService.findByReserveTicketId
     * ------------------------------------------------------------------------
     **/
    @Test
    void findByReserveTicketID() {

        ReservedTicketRecord record = new ReservedTicketRecord();
        record.setFlightId(randomUUID().toString());
        record.setFlightName("MarsFlight");
        record.setTicketId(randomUUID().toString());
        record.setDepartureLocation("FL");
        record.setArrivalLocation("Mars");
        record.setDateOfReservation(LocalDateTime.now().toString());
        record.setReservationClosed(true);
        record.setDateOfReservationClosed(LocalDateTime.now().toString());
        record.setNumberOfSeatsReserved(10);
        record.setPurchasedTicket(true);


        when(reservedTicketRepository.findById(record.getTicketId())).thenReturn(Optional.of(record));

        ReservedTicket reservedTicket = reservedTicketService.findByReservedTicketId(record.getTicketId());

        Assertions.assertNotNull(reservedTicket);
        Assertions.assertEquals(record.getFlightId(), reservedTicket.getFlightId(), "Flight id Matches");
        Assertions.assertEquals(record.getFlightName(), reservedTicket.getFlightName(), "Flight names match ");
        Assertions.assertEquals(record.getTicketId(), reservedTicket.getTicketId(), "TicketId matches ");
        Assertions.assertEquals(record.getDepartureLocation(), reservedTicket.getDepartureLocation(), "Locations Matche ");
        Assertions.assertEquals(record.getArrivalLocation(), reservedTicket.getArrivalLocation(), " Arrival locations match");
        Assertions.assertEquals(record.getDateOfReservation(), reservedTicket.getDateOfReservation(), " Reservation dates match");
        Assertions.assertEquals(record.getReservationClosed(), reservedTicket.getReservationClosed(), "");
        Assertions.assertEquals(record.getDateOfReservationClosed(), reservedTicket.getDateOfReservationClosed(), "");
        Assertions.assertEquals(record.getNumberOfSeatsReserved(), reservedTicket.getNumberOfSeatsReserved(), "10");
        Assertions.assertEquals(record.getPurchasedTicket(), reservedTicket.getPurchasedTicket(), "");




    }

    @Test
    void find_ReserveTicketID_by_TicketID_TicketId_Null(){
        ReservedTicketRecord record = new ReservedTicketRecord();
        record.setFlightId(randomUUID().toString());
        record.setFlightName("MarsFlight");
        record.setTicketId(randomUUID().toString());
        record.setDepartureLocation("FL");
        record.setArrivalLocation("Mars");
        record.setDateOfReservation(LocalDateTime.now().toString());
        record.setReservationClosed(true);
        record.setDateOfReservationClosed(LocalDateTime.now().toString());
        record.setNumberOfSeatsReserved(10);
        record.setPurchasedTicket(true);



        when(reservedTicketRepository.findById(record.getTicketId())).thenReturn(Optional.empty());

        ReservedTicket reservedTicket = reservedTicketService.findByReservedTicketId(record.getTicketId());
        Assertions.assertNull(reservedTicket,"Ticket Does not Exist");

//        Assertions.assertNotNull(reservedTicket);
//        Assertions.assertEquals(record.getFlightId(), reservedTicket.getFlightId(), "Flight id Matches");
//        Assertions.assertEquals(record.getFlightName(), reservedTicket.getFlightName(), "Flight names match ");
//        Assertions.assertEquals(record.getTicketId(), reservedTicket.getTicketId(), "TicketId matches ");
//        Assertions.assertEquals(record.getDepartureLocation(), reservedTicket.getDepartureLocation(), "Locations Matche ");
//        Assertions.assertEquals(record.getArrivalLocation(), reservedTicket.getArrivalLocation(), " Arrival locations match");
//        Assertions.assertEquals(record.getDateOfReservation(), reservedTicket.getDateOfReservation(), " Reservation dates match");
//        Assertions.assertEquals(record.getReservationClosed(), reservedTicket.getReservationClosed(), "");
//        Assertions.assertEquals(record.getDateOfReservationClosed(), reservedTicket.getDateOfReservationClosed(), "");
//        Assertions.assertEquals(record.getNumberOfSeatsReserved(), reservedTicket.getNumberOfSeatsReserved(), "10");
//        Assertions.assertEquals(record.getPurchasedTicket(), reservedTicket.getPurchasedTicket(), "");



    }



    /**
     * ------------------------------------------------------------------------
     * reservedTicketService.findByFlightId
     * ------------------------------------------------------------------------
     **/

    @Test
    void find_reservedTickeks_byFlightID() {

        String flightId = "validFlightID";
        List<ReservedTicketRecord> reservedTicketRecords = new ArrayList<>();
        ReservedTicketRecord record = new ReservedTicketRecord();
        record.setFlightId(flightId);
        record.setFlightName("MarsFLight");
        record.setTicketId(randomUUID().toString());
        record.setDepartureLocation("FL");
        record.setArrivalLocation("MARS");
        record.setDateOfReservation(LocalDateTime.now().toString());
        record.setReservationClosed(false);
        record.setDateOfReservationClosed(LocalDateTime.now().toString());
        record.setNumberOfSeatsReserved(10);
        record.setPurchasedTicket(true);

        ReservedTicketRecord record2 = new ReservedTicketRecord();
        record2.setFlightId(flightId);
        record2.setFlightName("MarsFLight");
        record2.setTicketId(randomUUID().toString());
        record2.setDepartureLocation("FL");
        record2.setArrivalLocation("MARS");
        record2.setDateOfReservation(LocalDateTime.now().toString());
        record2.setReservationClosed(false);
        record2.setDateOfReservationClosed(LocalDateTime.now().toString());
        record2.setNumberOfSeatsReserved(10);
        record2.setPurchasedTicket(true);

        ReservedTicketRecord record3 = new ReservedTicketRecord();
        record3.setFlightId(flightId);
        record3.setFlightName("MarsFLight");
        record3.setTicketId(randomUUID().toString());
        record3.setDepartureLocation("FL");
        record3.setArrivalLocation("MARS");
        record3.setDateOfReservation(LocalDateTime.now().toString());
        record3.setReservationClosed(false);
        record3.setDateOfReservationClosed(LocalDateTime.now().toString());
        record3.setNumberOfSeatsReserved(10);
        record3.setPurchasedTicket(true);

        reservedTicketRecords.add(record);
        reservedTicketRecords.add(record2);
        reservedTicketRecords.add(record3);


        List<ReservedTicket> reservedTicketList = reservedTicketService.findByFlightId("validFlightID");


        Assertions.assertNotNull(reservedTicketList);
        Assertions.assertEquals(reservedTicketList.size(), 0);
        for (ReservedTicket reservedTicket : reservedTicketList) {
            if (reservedTicket.getTicketId().equals(record.getTicketId())) {
                Assertions.assertEquals(record.getFlightId(), reservedTicket.getFlightId(), "The Flight Id's Match");

                Assertions.assertEquals(record.getFlightName(), reservedTicket.getFlightName(), "Flight names match");
                Assertions.assertEquals(record.getTicketId(), reservedTicket.getTicketId(), "TicketIds match");
                Assertions.assertEquals(record.getDepartureLocation(), reservedTicket.getDepartureLocation(), "Departure Locations match");
                Assertions.assertEquals(record.getArrivalLocation(), reservedTicket.getArrivalLocation(), "Arrival Locations match");
                Assertions.assertEquals(record.getDateOfReservation(), reservedTicket.getDateOfReservation(), "Reservation dates match");
                Assertions.assertEquals(record.getReservationClosed(), reservedTicket.getReservationClosed(), "");
                Assertions.assertEquals(record.getDateOfReservationClosed(), reservedTicket.getDateOfReservationClosed(), "closing dates match");
                Assertions.assertEquals(record.getNumberOfSeatsReserved(), reservedTicket.getNumberOfSeatsReserved(), "Seat reservation numbers match");
                Assertions.assertEquals(record.getPurchasedTicket(), reservedTicket.getPurchasedTicket(), "numbers of tickets purchased match");

            } else if (reservedTicket.getTicketId().equals(record2.getTicketId())) {
                Assertions.assertEquals(record2.getFlightId(), reservedTicket.getFlightId(), "The Flight Id's Match");

                Assertions.assertEquals(record2.getFlightName(), reservedTicket.getFlightName(), "Flight names match");
                Assertions.assertEquals(record2.getTicketId(), reservedTicket.getTicketId(), "TicketIds match");
                Assertions.assertEquals(record2.getDepartureLocation(), reservedTicket.getDepartureLocation(), "Departure Locations match");
                Assertions.assertEquals(record2.getArrivalLocation(), reservedTicket.getArrivalLocation(), "Arrival Locations match");
                Assertions.assertEquals(record2.getDateOfReservation(), reservedTicket.getDateOfReservation(), "Reservation dates match");
                Assertions.assertEquals(record2.getReservationClosed(), reservedTicket.getReservationClosed(), "");
                Assertions.assertEquals(record2.getDateOfReservationClosed(), reservedTicket.getDateOfReservationClosed(), "closing dates match");
                Assertions.assertEquals(record2.getNumberOfSeatsReserved(), reservedTicket.getNumberOfSeatsReserved(), "Seat reservation numbers match");
                Assertions.assertEquals(record2.getPurchasedTicket(), reservedTicket.getPurchasedTicket(), "numbers of tickets purchased match");

            } else if (reservedTicket.getTicketId().equals(record3.getTicketId())) {

                Assertions.assertEquals(record3.getFlightId(), reservedTicket.getFlightId(), "The Flight Id's Match");

                Assertions.assertEquals(record3.getFlightName(), reservedTicket.getFlightName(), "Flight names match");
                Assertions.assertEquals(record3.getTicketId(), reservedTicket.getTicketId(), "TicketIds match");
                Assertions.assertEquals(record3.getDepartureLocation(), reservedTicket.getDepartureLocation(), "Departure Locations match");
                Assertions.assertEquals(record3.getArrivalLocation(), reservedTicket.getArrivalLocation(), "Arrival Locations match");
                Assertions.assertEquals(record3.getDateOfReservation(), reservedTicket.getDateOfReservation(), "Reservation dates match");
                Assertions.assertEquals(record3.getReservationClosed(), reservedTicket.getReservationClosed(), "");
                Assertions.assertEquals(record3.getDateOfReservationClosed(), reservedTicket.getDateOfReservationClosed(), "closing dates match");
                Assertions.assertEquals(record3.getNumberOfSeatsReserved(), reservedTicket.getNumberOfSeatsReserved(), "Seat reservation numbers match");
                Assertions.assertEquals(record3.getPurchasedTicket(), reservedTicket.getPurchasedTicket(), "numbers of tickets purchased match");
            } else {
                Assertions.fail("NO");
            }


        }
    }

    /** ------------------------------------------------------------------------
     *  reservedTicketService.updateReserveTicket
     *  ------------------------------------------------------------------------ **/

    @Test
    void updateReservedTicket(){

        ReservedTicketRecord record = new ReservedTicketRecord();
        record.setFlightId(randomUUID().toString());
        record.setFlightName("MarsFLight");
        record.setTicketId(randomUUID().toString());
        record.setDepartureLocation("FL");
        record.setArrivalLocation("MARS");
        record.setDateOfReservation(LocalDateTime.now().toString());
        record.setReservationClosed(false);
        record.setDateOfReservationClosed(LocalDateTime.now().toString());
        record.setNumberOfSeatsReserved(10);
        record.setPurchasedTicket(true);


        ReservedTicket reservedTicket = new ReservedTicket(
                record.getFlightId(),
                record.getFlightName(),
                record.getTicketId(),
                record.getDepartureLocation(),
                record.getArrivalLocation(),
                record.getDateOfReservation(),
                record.getReservationClosed(),
                record.getDateOfReservationClosed(),
                record.getNumberOfSeatsReserved(),
                record.getPurchasedTicket());

        ArgumentCaptor<ReservedTicketRecord> recordCaptor = ArgumentCaptor.forClass(ReservedTicketRecord.class);

        reservedTicketService.updateReserveTicket(reservedTicket);

        verify(reservedTicketRepository).save(recordCaptor.capture());
        ReservedTicketRecord storedRecord = recordCaptor.getValue();


        Assertions.assertNotNull(reservedTicket);
        Assertions.assertEquals(storedRecord.getFlightId(), reservedTicket.getFlightId(), "The Flight Id's Match");
        Assertions.assertEquals(storedRecord.getFlightName(), reservedTicket.getFlightName(), "Flight names match");
        Assertions.assertEquals(storedRecord.getTicketId(), reservedTicket.getTicketId(), "TicketIds match");
        Assertions.assertEquals(storedRecord.getDepartureLocation(), reservedTicket.getDepartureLocation(), "Departure Locations match");
        Assertions.assertEquals(storedRecord.getArrivalLocation(), reservedTicket.getArrivalLocation(), "Arrival Locations match");
        Assertions.assertEquals(storedRecord.getDateOfReservation(), reservedTicket.getDateOfReservation(), "Reservation dates match");
        Assertions.assertEquals(storedRecord.getReservationClosed(), reservedTicket.getReservationClosed(), "");
        Assertions.assertEquals(storedRecord.getDateOfReservationClosed(), reservedTicket.getDateOfReservationClosed(), "closing dates match");
        Assertions.assertEquals(storedRecord.getNumberOfSeatsReserved(), reservedTicket.getNumberOfSeatsReserved(), "Seat reservation numbers match");
        Assertions.assertEquals(storedRecord.getPurchasedTicket(), reservedTicket.getPurchasedTicket(), "numbers of tickets purchased match");




    }
}



/*





 */