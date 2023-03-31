package com.kenzie.appserver.service;

import com.kenzie.appserver.IntegrationTest;
import com.kenzie.appserver.config.CacheStore;
import com.kenzie.appserver.service.model.Flight;
import net.andreinc.mockneat.MockNeat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;

import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@IntegrationTest
public class CacheManagerTest {

    @Autowired
    CacheManager cacheManager;

    @Autowired
    FlightService flightService;

    @Autowired
    private CacheStore Cache;

    private final MockNeat mockNeat = MockNeat.threadLocal();

    @Test
    public void flightCache_InsertIntoCache() throws Exception {
        String flightId = UUID.randomUUID().toString();
        String flightName = mockNeat.strings().valStr();
        String date = LocalDate.now().toString();
        String departureLocation = "FL";
        String arrivalLocation = "MARS";
        Double ticketBasePrice = 100000.0;

        Flight flight = new Flight(flightId,
                flightName,
                date,
                departureLocation,
                arrivalLocation,
                10,
                ticketBasePrice,
                false);
        flightService.addNewFlight(flight);
        flightService.findByFlightId(flightId);

        Flight flightFromCache = Cache.get(flight.getFlightId());

        assertThat(flightFromCache).isNotNull();
        assertThat(flightFromCache.getFlightId()).isEqualTo(flightId);
    }

    @Test
    public void flightCacheUpdate_EvictFromCache() throws Exception {
        String flightId = UUID.randomUUID().toString();
        String flightName = mockNeat.strings().valStr();
        String date = LocalDate.now().toString();
        String departureLocation = "FL";
        String arrivalLocation = "MARS";
        Double ticketBasePrice = 100000.0;

        Flight flight = new Flight(flightId,
                flightName,
                date,
                departureLocation,
                arrivalLocation,
                10,
                ticketBasePrice,
                false);
        flightService.addNewFlight(flight);
        flightService.findByFlightId(flightId);

        Flight flightFromCache = Cache.get(flight.getFlightId());

        flightService.updateFlight(flight);

        Flight flightFromCacheAfterUpdate = Cache.get(flight.getFlightId());

        assertThat(flightFromCache).isNotNull();
        assertThat(flightFromCache.getFlightId()).isEqualTo(flightId);
        assertThat(flightFromCacheAfterUpdate).isNull();
    }
}


