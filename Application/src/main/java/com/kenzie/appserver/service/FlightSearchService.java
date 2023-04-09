package com.kenzie.appserver.service;

import com.kenzie.appserver.repositories.FlightRepository;
import com.kenzie.appserver.repositories.model.FlightRecord;
import com.kenzie.appserver.service.exceptions.ItemNotFoundException;
import com.kenzie.appserver.service.model.Flight;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class FlightSearchService {
    @Autowired
    FlightRepository flightRepository;

    public FlightSearchService(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    public List<Flight> searchFlights(LocalDate date, String departureLocation, String arrivalLocation) {
        //        List<Flight> flights = new ArrayList<>();
        //        Flight flight1 = new Flight("1234455678", "NAS1005", "2003-07-01", "FL", "MARS", 100, 100000.00, false);
        //        flights.add(flight1);
        //        Flight flight2 = new Flight("1234455678", "NAS1005", "2003-07-01", "FL", "MARS", 100, 100000.00, false);
        //        flights.add(flight2);
        //        return flights;
        return findFlights(date, departureLocation, arrivalLocation);
    }

    public List<Flight> findFlights(LocalDate date, String departureLocation, String arrivalLocation) {
        List<Flight> flights = new ArrayList<>();
        Iterable<FlightRecord> flightIterator = flightRepository.findAll();

        for (FlightRecord record: flightIterator){
            if(date.toString().equals(record.getDate())
                    && departureLocation.equals(record.getDepartureLocation())
                    && arrivalLocation.equals(record.getArrivalLocation()) ){
                flights.add(new Flight(record.getId(),
                        record.getFlightName(),
                        record.getDate(),
                        record.getDepartureLocation(),
                        record.getArrivalLocation(),
                        record.getTotalSeatCapacity(),
                        record.getTicketBasePrice(),
                        record.getReservationClosed()));
            }
        }
        return flights;
    }

}
