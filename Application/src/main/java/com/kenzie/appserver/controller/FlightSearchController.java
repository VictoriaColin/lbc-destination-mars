package com.kenzie.appserver.controller;

import com.kenzie.appserver.controller.model.*;
import com.kenzie.appserver.service.FlightSearchService;
import com.kenzie.appserver.service.FlightService;
import com.kenzie.appserver.service.model.Flight;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static java.util.UUID.randomUUID;

@RestController
@RequestMapping("/flight")
public class FlightSearchController {

    @Autowired
    private FlightService flightService;

    @Autowired
    private FlightSearchService flightSearchService;

    @PostMapping("/search")
    public ResponseEntity<List<FlightResponse>> searchFlights(@RequestBody FlightSearchRequest request) {
        LocalDate date = request.getDate();
        String arrivalLocation = request.getArrivalLocation();
        String departureLocation = request.getDepartureLocation();
        System.out.println(departureLocation +" " + arrivalLocation + " " + date + " ");

        List<Flight> flights = flightSearchService.searchFlights(date, departureLocation, arrivalLocation);

        List<FlightResponse> flightResponses = new ArrayList<>();
        for (Flight flight : flights){
            FlightResponse flightResponse = new FlightResponse();
            flightResponse.setFlightId(flight.getFlightId());
            flightResponse.setFlightName(flight.getFlightName());
            flightResponse.setDate(flight.getDate());
            flightResponse.setDepartureLocation(flight.getDepartureLocation());
            flightResponse.setArrivalLocation(flight.getArrivalLocation());
            flightResponse.setTicketBasePrice(flight.getTicketBasePrice());
            flightResponse.setReservationClosed(flight.getReservationClosed());
            flightResponses.add(flightResponse);
        }
        return ResponseEntity.ok(flightResponses);
    }

    @GetMapping("/{flightId}")
    public ResponseEntity<FlightResponse> searchFlightById(@PathVariable("flightId") String flightId) {
        Flight flight = flightService.findByFlightId(flightId);
        // If there are no flights, then return a 204
        if (flight == null) {
            return ResponseEntity.notFound().build();
        }
        // Otherwise, convert it into a GetFlightResponse and return it
        FlightResponse flightResponse = createFlightResponse(flight);
        return ResponseEntity.ok(flightResponse);
    }

    @GetMapping
    public ResponseEntity<List<FlightResponse>> getAllFlights() {
        List<Flight> flights = flightService.findAllFlights();
        // If there are no flights, then return a 204
        if (flights == null ||  flights.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        // Otherwise, convert the List of Flight objects into a List of GetFlightResponse and return it
        List<FlightResponse> response = new ArrayList<>();
        for (Flight flight : flights) {
            response.add(this.createFlightResponse(flight));
        }

        return ResponseEntity.ok(response);
    }
    // todo
    @PostMapping
    public ResponseEntity<FlightResponse> addNewFlight(@RequestBody AddFlightRequest request) {
        //System.out.println("POST /flight " +" name " +getFlightRequest.getName());

        Flight flight = new Flight(randomUUID().toString(),
                request.getName(),
                request.getDate().toString(),
                request.getDepartureLocation(),
                request.getArrivalLocation(),
                10,
                request.getTicketBasePrice(),
               false);
        flightService.addNewFlight(flight);

        FlightResponse flightResponse = createFlightResponse(flight);

        return ResponseEntity.created(URI.create("/flight/" + flightResponse.getFlightId())).body(flightResponse);
    }
    @PutMapping
    public ResponseEntity<FlightResponse> updateFlight(@RequestBody FlightUpdateRequest flightUpdateRequest) {
        Flight flight = new Flight(flightUpdateRequest.getFlightId(),
                flightUpdateRequest.getFlightName(),
                flightUpdateRequest.getDate().toString(),
                flightUpdateRequest.getDepartureLocation(),
                flightUpdateRequest.getArrivalLocation(),
                flightUpdateRequest.getTotalSeatCapacity(),
                flightUpdateRequest.getTicketBasePrice(),
                flightUpdateRequest.getReservationClosed());
        FlightResponse flightResponse = createFlightResponse(flight);
        return ResponseEntity.ok(flightResponse);
    }
    @DeleteMapping("/{flightId}")
    public ResponseEntity deleteFlightById(@PathVariable("flightId") String flightId) {
        flightService.deleteFlight(flightId);
        return ResponseEntity.noContent().build();
        //return ResponseEntity.status((204)).build();
    }

    // Helper method
    private FlightResponse createFlightResponse(Flight flight) {
        FlightResponse flightResponse = new FlightResponse();
        flightResponse.setFlightId(flight.getFlightId());
        flightResponse.setFlightName(flight.getFlightName());
        flightResponse.setDate(flight.getDate());
        flightResponse.setArrivalLocation(flight.getArrivalLocation());
        flightResponse.setDepartureLocation(flight.getDepartureLocation());
        // todo: will have to make a change here
        flightResponse.setNumberOfSeatsReserved(flight.getTotalSeatCapacity());
        flightResponse.setTicketBasePrice(flight.getTicketBasePrice());
        flightResponse.setReservationClosed(flight.getReservationClosed());

        return flightResponse;
    }



}
