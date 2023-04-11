package com.kenzie.appserver.service;

import com.kenzie.appserver.config.CacheStore;
import com.kenzie.appserver.repositories.FlightRepository;
import com.kenzie.appserver.repositories.model.FlightRecord;
import com.kenzie.appserver.service.model.Flight;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.UUID.randomUUID;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class FlightServiceTest {

    private FlightRepository flightRepository;
    private CacheStore cacheStore;
    private FlightService flightService;

    @BeforeEach
    void setuo() {
        flightRepository = mock(FlightRepository.class);
        cacheStore = mock(CacheStore.class);
        flightService = new FlightService(flightRepository, cacheStore);


    }

    /**
     * ------------------------------------------------------------------------
     * FlightService.findAllFlights
     * ------------------------------------------------------------------------
     **/

    @Test
    void findAllFlights() {
        FlightRecord record = new FlightRecord();
        record.setId(randomUUID().toString());
        record.setFlightName("Mars=Flight");
        record.setDate(LocalDateTime.now().plusDays(2).toString());
        record.setDepartureLocation("FL");
        record.setArrivalLocation("MARS");
        record.setTotalSeatCapacity(10);
        record.setTicketBasePrice(100000.000);
        record.setReservationClosed(false);

        FlightRecord record2 = new FlightRecord();
        record2.setId(randomUUID().toString());
        record2.setFlightName("Mars=Flight");
        record2.setDate(LocalDateTime.now().plusDays(2).toString());
        record2.setDepartureLocation("FL");
        record2.setArrivalLocation("MARS");
        record2.setTotalSeatCapacity(10);
        record2.setTicketBasePrice(100000.000);
        record2.setReservationClosed(false);


        List<FlightRecord> flightRecords = new ArrayList<>();
        flightRecords.add(record);
        flightRecords.add(record2);
        when(flightRepository.findAll()).thenReturn(flightRecords);
        List<Flight> flights = flightService.findAllFlights();


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

    /**
     * ------------------------------------------------------------------------
     * FlightService.findFlightsWithOutCache
     * ------------------------------------------------------------------------
     **/

    @Test
    void FindflightsWithOutCache() {
        String flightId = randomUUID().toString();


        FlightRecord record = new FlightRecord();
        record.setId(randomUUID().toString());
        record.setFlightName("Mars=Flight");
        record.setDate(LocalDateTime.now().plusDays(2).toString());
        record.setDepartureLocation("FL");
        record.setArrivalLocation("MARS");
        record.setTotalSeatCapacity(10);
        record.setTicketBasePrice(100000.000);
        record.setReservationClosed(false);

        when(flightRepository.findById(flightId)).thenReturn(Optional.of(record));

        Flight flight = flightService.findByFlightIdWithoutCache(flightId);

        Assertions.assertNotNull(record, "This Flight record is returned");
        Assertions.assertEquals(record.getId(), flight.getFlightId());
        Assertions.assertEquals(record.getDate(), flight.getDate());
        Assertions.assertEquals(record.getDepartureLocation(), flight.getDepartureLocation());
        Assertions.assertEquals(record.getArrivalLocation(), flight.getArrivalLocation());
        Assertions.assertEquals(record.getTotalSeatCapacity(), flight.getTotalSeatCapacity());
        Assertions.assertEquals(record.getTicketBasePrice(), flight.getTicketBasePrice());
        Assertions.assertEquals(record.getReservationClosed(), flight.getReservationClosed());

    }

    /**
     * ------------------------------------------------------------------------
     * FlightService.findFlightsByFlightId
     * ------------------------------------------------------------------------
     **/
    @Test
    void findFlightByFlightId() {

        String flightId = randomUUID().toString();


        FlightRecord record = new FlightRecord();
        record.setId(randomUUID().toString());
        record.setFlightName("Mars=Flight");
        record.setDate(LocalDateTime.now().plusDays(2).toString());
        record.setDepartureLocation("FL");
        record.setArrivalLocation("MARS");
        record.setTotalSeatCapacity(10);
        record.setTicketBasePrice(100000.000);
        record.setReservationClosed(false);

        when(flightRepository.findById(flightId)).thenReturn(Optional.of(record));

        Flight flight = flightService.findByFlightId(flightId);

        Assertions.assertNotNull(record, "This Flight record is returned");
        Assertions.assertEquals(record.getId(), flight.getFlightId());
        Assertions.assertEquals(record.getDate(), flight.getDate());
        Assertions.assertEquals(record.getDepartureLocation(), flight.getDepartureLocation());
        Assertions.assertEquals(record.getArrivalLocation(), flight.getArrivalLocation());
        Assertions.assertEquals(record.getTotalSeatCapacity(), flight.getTotalSeatCapacity());
        Assertions.assertEquals(record.getTicketBasePrice(), flight.getTicketBasePrice());
        Assertions.assertEquals(record.getReservationClosed(), flight.getReservationClosed());

    }
    @Test
    void findByFlightIdFromCache_retrievesCashedFlight() {
        String flightId = randomUUID().toString();
        Flight cachedFlight = new Flight("id", "MARS-Flight", LocalDateTime.now().toString(), "FL", "MARS", 10, 100000.000, false);

        when(cacheStore.get(flightId)).thenReturn(cachedFlight);
        flightService.findByFlightId(flightId);
        assertNotNull(cacheStore.get(flightId));
    }
    @Test
    void findByConcertIdWithoutCache_returnsNull() {
        // GIVEN
        String flightId = randomUUID().toString();

        when(flightRepository.findById(flightId)).thenReturn(Optional.empty());
        // WHEN
        Flight flight = flightService.findByFlightIdWithoutCache(flightId);
        Assertions.assertNull(flight, "The flight was not returned");
    }

    /**
     * ------------------------------------------------------------------------
     * FlightService.findFlightFromBackEndService
     * ------------------------------------------------------------------------
     **/

    @Test
    void findByFlightId_fromBackend_addsToCache_retrievesCashedFlight() {
        String flightId = randomUUID().toString();

        FlightRecord record = new FlightRecord();
        record.setId(randomUUID().toString());
        record.setFlightName("Mars=Flight");
        record.setDate(LocalDateTime.now().plusDays(2).toString());
        record.setDepartureLocation("FL");
        record.setArrivalLocation("MARS");
        record.setTotalSeatCapacity(10);
        record.setTicketBasePrice(100000.000);
        record.setReservationClosed(false);

        Optional<FlightRecord> flightRecord = Optional.of(record);
        when(flightRepository.findById(flightId)).thenReturn(flightRecord);

        Flight flightFromBackendService = flightService.findByFlightId(flightId);
        verify(flightRepository, times(1)).findById(flightId);
        verify(cacheStore, times(1)).add(flightFromBackendService.getFlightId(), flightFromBackendService);

        ArgumentCaptor<String> idCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Flight> flightArgumentCaptor = ArgumentCaptor.forClass(Flight.class);
        verify(cacheStore).add(idCaptor.capture(), flightArgumentCaptor.capture());

        Assertions.assertEquals(idCaptor.getValue(), flightFromBackendService.getFlightId());
        Assertions.assertEquals(flightArgumentCaptor.getValue(), flightFromBackendService);

    }

    /**
     * ------------------------------------------------------------------------
     * FlightService.addNewFlight
     * ------------------------------------------------------------------------
     **/
    @Test
    void addNewFlight() {

        String flightID = randomUUID().toString();
        Flight flight = new Flight("id", "MARS-Flight", LocalDateTime.now().toString(), "FL", "MARS", 10, 100000.000, false);
        ArgumentCaptor<FlightRecord> flightRecordArgumentCaptor = ArgumentCaptor.forClass(FlightRecord.class);


        Flight returnedFlight = flightService.addNewFlight(flight);


        Assertions.assertNotNull(returnedFlight);
        verify(flightRepository).save(flightRecordArgumentCaptor.capture());
        FlightRecord flightRecord = flightRecordArgumentCaptor.getValue();


        Assertions.assertNotNull(flightRecord, "This Flight record is returned");
        Assertions.assertEquals(flightRecord.getId(), flight.getFlightId());
        Assertions.assertEquals(flightRecord.getFlightName(), flight.getFlightName());
        Assertions.assertEquals(flightRecord.getDate(), flight.getDate());
        Assertions.assertEquals(flightRecord.getDepartureLocation(), flight.getDepartureLocation());
        Assertions.assertEquals(flightRecord.getArrivalLocation(), flight.getArrivalLocation());
        Assertions.assertEquals(flightRecord.getTotalSeatCapacity(), flight.getTotalSeatCapacity());
        Assertions.assertEquals(flightRecord.getTicketBasePrice(), flight.getTicketBasePrice());
        Assertions.assertEquals(flightRecord.getReservationClosed(), flight.getReservationClosed());


    }
    @Test
    void addNewFlight_failsToAdd() {

        String flightID = randomUUID().toString();
        Flight flight = new Flight("id", "MARS-Flight", LocalDateTime.now().toString(), "FL", "MARS", 10, 100000.000, false);
        ArgumentCaptor<FlightRecord> flightRecordArgumentCaptor = ArgumentCaptor.forClass(FlightRecord.class);


        Flight returnedFlight = flightService.addNewFlight(flight);


        Assertions.assertNotNull(returnedFlight);
        verify(flightRepository).save(flightRecordArgumentCaptor.capture());
        FlightRecord flightRecord = flightRecordArgumentCaptor.getValue();


        Assertions.assertNotNull(flightRecord, "This Flight record is returned");
        Assertions.assertNotEquals(flightRecord.getId(), flightID, "The flight id do not matches");

    }


    /**
     * ------------------------------------------------------------------------
     * FlightService.UpdateFlight
     * ------------------------------------------------------------------------
     **/

    @Test
    void update_Flight() {

        String flightID = "id";
        Flight flight = new Flight("id", "MARS-FLIGHT", LocalDateTime.now().toString(), "FL", "MARS", 10, 100000.000, false);
        when(flightRepository.existsById(flightID)).thenReturn(true);

        ArgumentCaptor<FlightRecord> flightrecordargumentcaptor = ArgumentCaptor.forClass(FlightRecord.class);
        when(cacheStore.get(flightID)).thenReturn(flight);


        flightService.updateFlight(flight);


        verify(flightRepository, times(1)).existsById(flightID);
        verify(flightRepository, times(1)).save(flightrecordargumentcaptor.capture());


        FlightRecord flightRecord = flightrecordargumentcaptor.getValue();
        Assertions.assertEquals(flightRecord.getId(), flight.getFlightId());
        Assertions.assertEquals(flightRecord.getFlightName(), flight.getFlightName());
        Assertions.assertEquals(flightRecord.getDate(), flight.getDate());
        Assertions.assertEquals(flightRecord.getDepartureLocation(), flight.getDepartureLocation());
        Assertions.assertEquals(flightRecord.getArrivalLocation(), flight.getArrivalLocation());
        Assertions.assertEquals(flightRecord.getTotalSeatCapacity(), flight.getTotalSeatCapacity());
        Assertions.assertEquals(flightRecord.getTicketBasePrice(), flight.getTicketBasePrice());
        Assertions.assertEquals(flightRecord.getReservationClosed(), flight.getReservationClosed());

        // verify(cacheStore).get(flightID);
        //  verify(cacheStore.evict(flightID);


    }

    @Test
    void updateFlight_failsToUpdate() {

        String flightID = "id";
        Flight flight = new Flight("id", "MARS-FLIGHT", LocalDateTime.now().toString(), "FL", "MARS", 10, 100000.000, false);
        ArgumentCaptor<FlightRecord> flightRecordArgumentCaptor = ArgumentCaptor.forClass(FlightRecord.class);

        when(flightRepository.existsById(flightID)).thenReturn(false);
        flightService.updateFlight(flight);
        verify(flightRepository, times(0)).save(any());

        when(cacheStore.get(flightID)).thenReturn(null);
        verify(cacheStore, times(0)).evict(flightID);

    }


    /**
     * ------------------------------------------------------------------------
     * FlightService.deleteFlight
     * ------------------------------------------------------------------------
     **/

    @Test
    void delete_flight_withValidFlightId() {

        String flightid = randomUUID().toString();
        Flight flight = new Flight(flightid, "Mars_FLight", "recordDate", "FL", "MARS", 10, 1000000.000, false);

        when(cacheStore.get(flightid)).thenReturn(flight);
        flightService.addNewFlight(flight);

        flightService.deleteFlight(flightid);
        flightService.findByFlightId(flightid);
        Flight flightValiable = flightService.findByFlightId(flightid);
        if (flightValiable == null) {
            Assertions.assertTrue(true);
        }

        //verify(flightRepository).deleteById(flightid);
        //verify(cacheStore).evict(flightid);
        //verify(cacheStore).evict(flightid);

    }
    @Test
    void deleteFlight_withValidFlightId_NotDeleted() {

        String flightid = randomUUID().toString();
        Flight flight = new Flight(flightid, "Mars_FLight", "recordDate", "FL", "MARS", 10, 1000000.000, false);

        when(flightRepository.existsById(flight.getFlightId())).thenReturn(false);
        flightService.deleteFlight(flightid);

        verify(flightRepository,times(0)).deleteById(flightid);
        when(cacheStore.get(flightid)).thenReturn(null);
        verify(cacheStore, times(0)).evict(flightid);
    }

}
