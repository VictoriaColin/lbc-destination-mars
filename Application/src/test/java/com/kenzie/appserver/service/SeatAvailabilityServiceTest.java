package com.kenzie.appserver.service;


import com.kenzie.appserver.repositories.SeatRepository;
import com.kenzie.appserver.repositories.model.FlightRecord;
import com.kenzie.appserver.repositories.model.SeatRecord;
import com.kenzie.appserver.service.model.Seat;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;


import java.util.ArrayList;
import java.util.List;

import static java.util.UUID.randomUUID;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class SeatAvailabilityServiceTest {

    private SeatAvailabilityService subject;

    private SeatRepository seatRepository;

    @BeforeEach
    void setuo(){
        seatRepository = mock(SeatRepository.class);
        subject = new SeatAvailabilityService(seatRepository);
    }

    /** ------------------------------------------------------------------------
     *  SeatAvailabilityService.getSeats
     *  ------------------------------------------------------------------------ **/
    @Test
    void getAvailableSeats_returnsAllAvailable(){

        String flightId = randomUUID().toString();

        SeatRecord record1 = new SeatRecord();
        record1.setFlightId(flightId);
        record1.setSeatNumber("11");
        record1.setSeatReservationClosed(false);
        record1.setTicketId(null);

        SeatRecord record2 = new SeatRecord();
        record2.setFlightId(flightId);
        record2.setSeatNumber("22");
        record2.setSeatReservationClosed(false);
        record2.setTicketId(null);

        List<SeatRecord> seatRecord = new ArrayList<>();
        seatRecord.add(record1);
        seatRecord.add(record2);

        // when
        when(seatRepository.findAll()).thenReturn(seatRecord);//getSeatsByFlightId(flightId)
        List<Seat> seats = subject.getAllSeats(flightId);

        //then
        Assertions.assertNotNull(seats, "The Seat list is returned");
        Assertions.assertEquals(2,seats.size(), "All available seats provided");

    }

    @Test
    void getAvailableSeats_returnOnlyAvailable() {

        String flightId = randomUUID().toString();

        SeatRecord record1 = new SeatRecord();
        record1.setFlightId(flightId);
        record1.setSeatNumber("11");
        record1.setSeatReservationClosed(false);
        record1.setTicketId(null);

        SeatRecord record2 = new SeatRecord();
        record2.setFlightId(flightId);
        record2.setSeatNumber("22");
        record2.setSeatReservationClosed(true);
        record2.setTicketId(randomUUID().toString());

        List<SeatRecord> seatRecords = new ArrayList<>();
        seatRecords.add(record1);
        seatRecords.add(record2);

        // when
        when(seatRepository.findAll()).thenReturn(seatRecords);
        List<Seat> seats = subject.getAllSeats(flightId);

        //then
        Assertions.assertNotNull(seats, "The Seat list is returned");
        Assertions.assertEquals(1, seats.size(), "Only one seat is provided");

        for (Seat seat : seats) {
            if (seat.getFlightId().equals(record1.getFlightId())) {
                Assertions.assertEquals(seat.getFlightId(), record1.getFlightId(), "the flightId matches");
                Assertions.assertEquals(seat.getSeatNumber(), record1.getSeatNumber(), "the seatNumber matches");
                Assertions.assertEquals(seat.getTicketId(), record1.getTicketId(), "the ticketId matches");
                Assertions.assertEquals(seat.getSeatReservationClosed(), record1.getSeatReservationClosed(), "the seatReservation status matches");

            }else{
                Assertions.fail("Request failed");
        }

        }
    }

    @Test
    void addNewSeat(){
        String flightId = "SPX1002";
        String seatNumber = "22";
        Seat seat = new Seat(flightId,seatNumber,null,false);
        ArgumentCaptor<SeatRecord> seatRecordArgumentCaptor = ArgumentCaptor.forClass(SeatRecord.class);

        // WHEN
        Seat newSeat = subject.addNewSeat(seat);

        // THEN
        assertNotNull(newSeat);

        verify(seatRepository).save(seatRecordArgumentCaptor.capture());

        SeatRecord seatRecord = seatRecordArgumentCaptor.getValue();
        assertNotNull(seatRecord, "The seat record is returned");
        Assertions.assertEquals(seatRecord.getFlightId(),seat.getFlightId(),"The flight id matches");
        Assertions.assertEquals(seatRecord.getSeatNumber(),seat.getSeatNumber(),"The seat number matches");
        Assertions.assertEquals(seatRecord.getTicketId(),seat.getTicketId(),"The ticket id matches");
        Assertions.assertEquals(seatRecord.getSeatReservationClosed(),seat.getSeatReservationClosed(),"The reservation status matches");


    }



    @Test
    void updateReservation(){
        String flightId = "SPX1002";
        String seatNumber = "22";
        String ticketId = randomUUID().toString();
        Seat seat = new Seat(flightId,seatNumber,ticketId,false);

        //WHEN
        ArgumentCaptor<SeatRecord> seatRecordArgumentCaptor = ArgumentCaptor.forClass(SeatRecord.class);
        when(seatRepository.existsById(seat.getFlightId())).thenReturn(true);
        subject.updateSeat(seat);

        verify(seatRepository,times(1)).existsById(flightId);
        verify(seatRepository,times(1)).save(seatRecordArgumentCaptor.capture());

        SeatRecord seatRecord = seatRecordArgumentCaptor.getValue();
        assertNotNull(seatRecord, "The seat record is returned");
        Assertions.assertEquals(seatRecord.getFlightId(),seat.getFlightId(),"The flight id matches");
        Assertions.assertEquals(seatRecord.getSeatNumber(),seat.getSeatNumber(),"The seat number matches");
        Assertions.assertEquals(seatRecord.getTicketId(),seat.getTicketId(),"The ticket id matches");
        Assertions.assertEquals(seatRecord.getSeatReservationClosed(),seat.getSeatReservationClosed(),"The reservation status matches");


    }


}
