package com.kenzie.appserver.service;

import com.kenzie.appserver.config.CacheStore;
import com.kenzie.appserver.repositories.FlightRepository;
import com.kenzie.appserver.repositories.model.FlightRecord;
import com.kenzie.appserver.service.model.Flight;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FlightService {
    private FlightRepository flightRepository;
    private CacheStore cache;


    @Autowired
    public FlightService(FlightRepository flightRepository, CacheStore cache) {
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
}
