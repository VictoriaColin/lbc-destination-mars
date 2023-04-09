package com.kenzie.appserver.service;

import com.amazonaws.services.dynamodbv2.xspec.L;
import com.kenzie.appserver.config.CacheStore;
import com.kenzie.appserver.repositories.FlightRepository;
import com.kenzie.appserver.repositories.model.FlightRecord;
import com.kenzie.appserver.service.exceptions.ItemNotFoundException;
import com.kenzie.appserver.service.model.Flight;
import org.apache.tomcat.util.http.fileupload.FileItemStream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static java.util.UUID.randomUUID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FlightSearchServiceTest {

    @Mock
    private FlightRepository flightRepository;
    @InjectMocks
    private FlightSearchService subject;

    @BeforeEach
    void setuo() {
        flightRepository = mock(FlightRepository.class);
        subject = new FlightSearchService(flightRepository);
    }

    @Test
    void findFlights_isSuccessful() {
        LocalDate date = LocalDate.parse("2003-07-01");
        String departureLocation = "FL";
        String arrivalLocation = "MARS";

        FlightRecord record = new FlightRecord();
        record.setId(randomUUID().toString());
        record.setFlightName("SPX2000");
        record.setDate(String.valueOf(date));
        record.setDepartureLocation(departureLocation);
        record.setArrivalLocation(arrivalLocation);
        record.setTotalSeatCapacity(10);
        record.setTicketBasePrice(100000.000);
        record.setReservationClosed(false);

        FlightRecord record2 = new FlightRecord();
        record2.setId(randomUUID().toString());
        record2.setFlightName("SPX2001");
        record2.setDate(String.valueOf(date));
        record2.setDepartureLocation(departureLocation);
        record2.setArrivalLocation(arrivalLocation);
        record2.setTotalSeatCapacity(10);
        record2.setTicketBasePrice(100000.000);
        record2.setReservationClosed(false);

        List<FlightRecord> flightRecords = new ArrayList<>();
        flightRecords.add(record);
        flightRecords.add(record2);
        when(flightRepository.findAll()).thenReturn(flightRecords);

        List<Flight> flights = subject.findFlights(date, departureLocation, arrivalLocation);

        Assertions.assertNotNull(flights, "The FLight list is returned");
        Assertions.assertEquals(2, flights.size(), "The are two Flights");

        for (Flight flight : flights) {
            if (flight.getFlightId().equals(record.getId())) {
                Assertions.assertEquals(record.getId(), flight.getFlightId(), "The Flight id matches");
                Assertions.assertEquals(record.getFlightName(), flight.getFlightName(), " The FLight names match");
                Assertions.assertEquals(record.getDate(), flight.getDate(), "The Flight date matches");
                Assertions.assertEquals(record.getTicketBasePrice(), flight.getTicketBasePrice(), "The ticket base price's match");
                Assertions.assertEquals(record.getReservationClosed(), flight.getReservationClosed(), "The reservation close dates match");

            } else if (flight.getFlightId().equals(record2.getId())) {
                Assertions.assertEquals(record2.getId(), flight.getFlightId(), "The Flight id matches");
                Assertions.assertEquals(record2.getFlightName(), flight.getFlightName(), " The FLight names match");
                Assertions.assertEquals(record2.getDate(), flight.getDate(), "The Flight date matches");
                Assertions.assertEquals(record2.getTicketBasePrice(), flight.getTicketBasePrice(), "The ticket base price's match");
                Assertions.assertEquals(record2.getReservationClosed(), flight.getReservationClosed(), "The reservation close dates match");


            } else {
                Assertions.fail("Flight returned that was not in the records!");
            }

        }
    }


    @Test
    void searchFlights_isSuccessful() {
        LocalDate date = LocalDate.parse("2003-07-01");
        String departureLocation = "FL";
        String arrivalLocation = "MARS";

        FlightRecord record = new FlightRecord();
        record.setId(randomUUID().toString());
        record.setFlightName("SPX2000");
        record.setDate(String.valueOf(date));
        record.setDepartureLocation(departureLocation);
        record.setArrivalLocation(arrivalLocation);
        record.setTotalSeatCapacity(10);
        record.setTicketBasePrice(100000.000);
        record.setReservationClosed(false);

        FlightRecord record2 = new FlightRecord();
        record2.setId(randomUUID().toString());
        record2.setFlightName("SPX2001");
        record2.setDate(String.valueOf(date));
        record2.setDepartureLocation(departureLocation);
        record2.setArrivalLocation(arrivalLocation);
        record2.setTotalSeatCapacity(10);
        record2.setTicketBasePrice(100000.000);
        record2.setReservationClosed(false);

        List<FlightRecord> flightRecords = new ArrayList<>();
        flightRecords.add(record);
        flightRecords.add(record2);
        when(flightRepository.findAll()).thenReturn(flightRecords);

        List<Flight> flights = subject.searchFlights(date, departureLocation, arrivalLocation);

        Assertions.assertNotNull(flights, "The FLight list is returned");
        Assertions.assertEquals(2, flights.size(), "The are two Flights");

        for (Flight flight : flights) {
            if (flight.getFlightId().equals(record.getId())) {
                Assertions.assertEquals(record.getId(), flight.getFlightId(), "The Flight id matches");
                Assertions.assertEquals(record.getFlightName(), flight.getFlightName(), " The FLight names match");
                Assertions.assertEquals(record.getDate(), flight.getDate(), "The Flight date matches");
                Assertions.assertEquals(record.getTicketBasePrice(), flight.getTicketBasePrice(), "The ticket base price's match");
                Assertions.assertEquals(record.getReservationClosed(), flight.getReservationClosed(), "The reservation close dates match");

            } else if (flight.getFlightId().equals(record2.getId())) {
                Assertions.assertEquals(record2.getId(), flight.getFlightId(), "The Flight id matches");
                Assertions.assertEquals(record2.getFlightName(), flight.getFlightName(), " The FLight names match");
                Assertions.assertEquals(record2.getDate(), flight.getDate(), "The Flight date matches");
                Assertions.assertEquals(record2.getTicketBasePrice(), flight.getTicketBasePrice(), "The ticket base price's match");
                Assertions.assertEquals(record2.getReservationClosed(), flight.getReservationClosed(), "The reservation close dates match");


            } else {
                Assertions.fail("Flight returned that was not in the records!");
            }

        }
    }
    @Test
    void searchFlights_isUnSuccessful_DateOfOneRecord_DoesNotMatch() {
        LocalDate date = LocalDate.parse("2003-07-01");
        String departureLocation = "FL";
        String arrivalLocation = "MARS";

        FlightRecord record = new FlightRecord();
        record.setId(randomUUID().toString());
        record.setFlightName("SPX2000");
        record.setDate(String.valueOf(date));
        record.setDepartureLocation(departureLocation);
        record.setArrivalLocation(arrivalLocation);
        record.setTotalSeatCapacity(10);
        record.setTicketBasePrice(100000.000);
        record.setReservationClosed(false);


        LocalDate date2 = LocalDate.parse("2003-07-03");
        FlightRecord record2 = new FlightRecord();
        record2.setId(randomUUID().toString());
        record2.setFlightName("SPX2001");
        record2.setDate(String.valueOf(date2));
        record2.setDepartureLocation(departureLocation);
        record2.setArrivalLocation(arrivalLocation);
        record2.setTotalSeatCapacity(10);
        record2.setTicketBasePrice(100000.000);
        record2.setReservationClosed(false);

        List<FlightRecord> flightRecords = new ArrayList<>();
        flightRecords.add(record);
        flightRecords.add(record2);
        when(flightRepository.findAll()).thenReturn(flightRecords);

        List<Flight> flights = subject.searchFlights(date, departureLocation, arrivalLocation);

        Assertions.assertNotNull(flights, "The FLight list is returned");
        Assertions.assertEquals(1, flights.size(), "The is only one available flights");

    }
    @Test
    void searchFlights_isUnSuccessful_ArrivalLocationDoesNotMatch() {
        LocalDate date = LocalDate.parse("2003-07-01");
        String departureLocation = "FL";
        String arrivalLocation = "MARS";

        FlightRecord record = new FlightRecord();
        record.setId(randomUUID().toString());
        record.setFlightName("SPX2000");
        record.setDate(String.valueOf(date));
        record.setDepartureLocation(departureLocation);
        record.setArrivalLocation(arrivalLocation);
        record.setTotalSeatCapacity(10);
        record.setTicketBasePrice(100000.000);
        record.setReservationClosed(false);

        FlightRecord record2 = new FlightRecord();
        record2.setId(randomUUID().toString());
        record2.setFlightName("SPX2001");
        record2.setDate(String.valueOf(date));
        record2.setDepartureLocation("MOON");
        record2.setArrivalLocation("MOON");
        record2.setTotalSeatCapacity(10);
        record2.setTicketBasePrice(100000.000);
        record2.setReservationClosed(false);

        List<FlightRecord> flightRecords = new ArrayList<>();
        flightRecords.add(record);
        flightRecords.add(record2);
        when(flightRepository.findAll()).thenReturn(flightRecords);

        List<Flight> flights = subject.searchFlights(date, "CA", arrivalLocation);

        Assertions.assertNotNull(flights, "The FLight list is returned");
        Assertions.assertEquals(0, flights.size(), "The are no available flights");

    }




    @Test
    void searchFlights_isUnSuccessful_DepartureLocationDoesNotMatch() {
        LocalDate date = LocalDate.parse("2003-07-01");
        String departureLocation = "FL";
        String arrivalLocation = "MARS";

        FlightRecord record = new FlightRecord();
        record.setId(randomUUID().toString());
        record.setFlightName("SPX2000");
        record.setDate(String.valueOf(date));
        record.setDepartureLocation(departureLocation);
        record.setArrivalLocation(arrivalLocation);
        record.setTotalSeatCapacity(10);
        record.setTicketBasePrice(100000.000);
        record.setReservationClosed(false);

        FlightRecord record2 = new FlightRecord();
        record2.setId(randomUUID().toString());
        record2.setFlightName("SPX2001");
        record2.setDate(String.valueOf(date));
        record2.setDepartureLocation(departureLocation);
        record2.setArrivalLocation(arrivalLocation);
        record2.setTotalSeatCapacity(10);
        record2.setTicketBasePrice(100000.000);
        record2.setReservationClosed(false);

        List<FlightRecord> flightRecords = new ArrayList<>();
        flightRecords.add(record);
        flightRecords.add(record2);
        when(flightRepository.findAll()).thenReturn(flightRecords);

        List<Flight> flights = subject.searchFlights(date, "CA", arrivalLocation);

        Assertions.assertNotNull(flights, "The FLight list is returned");
        Assertions.assertEquals(0, flights.size(), "The are no available flights");

    }


}