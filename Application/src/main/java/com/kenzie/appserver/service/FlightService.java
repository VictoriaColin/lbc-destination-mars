package com.kenzie.appserver.service;

//import com.kenzie.appserver.config.CacheStore;
import com.kenzie.appserver.config.CacheStore;
import com.kenzie.appserver.repositories.FlightRepository;
import com.kenzie.appserver.repositories.model.FlightRecord;
import com.kenzie.appserver.service.model.Flight;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FlightService {
    private FlightRepository flightRepository;
    private CacheStore cache;


    @Autowired
    public FlightService(FlightRepository flightRepository) {// , CacheStore cache
        this.flightRepository = flightRepository;
        this.cache = cache;

    }
    public List<Flight> findAllFlights() {
        List<Flight> flights = new ArrayList<>();

        Iterable<FlightRecord> flightIterator = flightRepository.findAll();
        for(FlightRecord record : flightIterator) {
                flights.add(new Flight(record.getId(),
                        record.getFlightName(),
                        record.getDate(),
                        record.getDepartureLocation(),
                        record.getArrivalLocation(),
                        record.getTotalSeatCapacity(),
                        record.getTicketBasePrice(),
                        record.getReservationClosed()));
        }
        return flights;
    }

    public Flight findByFlightIdWithoutCache(String flightId) {
        Optional<FlightRecord> optionalRecord = flightRepository.findById(flightId);

        if (optionalRecord.isPresent()) {
            FlightRecord record = optionalRecord.get();
            return new Flight(record.getId(),
                    record.getFlightName(),
                    record.getDate(),
                    record.getDepartureLocation(),
                    record.getArrivalLocation(),
                    record.getTotalSeatCapacity(),
                    record.getTicketBasePrice(),
                    record.getReservationClosed());
        } else {
            return null;
        }
    }
    public Flight findByFlightId(String flightId) {
        Flight cachedFlight = cache.get(flightId);
        // Check if flight is cached and return it if true
        if (cachedFlight != null) {
            return cachedFlight;
        }
        // if not cached, find the flight
        Flight flightFromBackendService = flightRepository
                .findById(flightId)
                .map(flight -> new Flight(flight.getId(),
                        flight.getFlightName(),
                        flight.getDate(),
                        flight.getDepartureLocation(),
                        flight.getArrivalLocation(),
                        flight.getTotalSeatCapacity(),
                        flight.getTicketBasePrice(),
                        flight.getReservationClosed()))
                .orElse(null);

        // if flight found, cache it
        if (flightFromBackendService != null) {
            cache.add(flightFromBackendService.getId(), flightFromBackendService);
        }
        // return flight
        return flightFromBackendService;
    }

    public Flight addNewFlight(Flight flight) {
        FlightRecord flightRecord = new FlightRecord();
        flightRecord.setFlightName(flight.getFlightName());
        flightRecord.setDate(flight.getDate());
        flightRecord.setId(flight.getId());
        flightRecord.setDepartureLocation(flight.getDepartureLocation());
        flightRecord.setArrivalLocation(flight.getArrivalLocation());
        flightRecord.setTotalSeatCapacity(flight.getTotalSeatCapacity());
        flightRecord.setTicketBasePrice(flight.getTicketBasePrice());
        flightRecord.setReservationClosed(flight.getReservationClosed());
        flightRepository.save(flightRecord);
        return flight;
    }

    public void updateFlight(Flight flight) {
        if (flightRepository.existsById(flight.getId())) {
            FlightRecord flightRecord = new FlightRecord();
            flightRecord.setId(flight.getId());
            flightRecord.setDate(flight.getDate());
            flightRecord.setId(flight.getId());
            flightRecord.setDepartureLocation(flight.getDepartureLocation());
            flightRecord.setArrivalLocation(flight.getArrivalLocation());
            flightRecord.setTotalSeatCapacity(flight.getTotalSeatCapacity());
            flightRecord.setTicketBasePrice(flight.getTicketBasePrice());
            flightRecord.setReservationClosed(flight.getReservationClosed());
            flightRepository.save(flightRecord);
            cache.evict(flight.getId());
        }
    }

    public void deleteFlight(String flightId) {
        if (flightRepository.existsById(flightId)) {
            FlightRecord flightRecord = new FlightRecord();
            flightRecord.setId(flightId);
            flightRepository.delete(flightRecord);
            cache.evict(flightId);
        }
    }

}
