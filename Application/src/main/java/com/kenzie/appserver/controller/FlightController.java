package com.kenzie.appserver.controller;

import com.kenzie.appserver.controller.model.GetFlightRequest;
import com.kenzie.appserver.controller.model.GetFlightResponse;
import com.kenzie.appserver.service.FlightService;
import com.kenzie.appserver.service.model.Flight;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static java.util.UUID.randomUUID;

@RestController

@RequestMapping("/flight")
public class FlightController {

    private FlightService flightService;

    FlightController(FlightService flightService){
        this.flightService = flightService;
    }

    @GetMapping("/{flightId}")
    public ResponseEntity<GetFlightResponse> searchFlightById(@PathVariable("flightId") String flightId) {
        Flight flight = flightService.findByFlightId(flightId);
        // If there are no flights, then return a 204
        if (flight == null) {
            return ResponseEntity.notFound().build();
        }
        // Otherwise, convert it into a GetFlightResponse and return it
        GetFlightResponse getFlightResponse = createFlightResponse(flight);
        return ResponseEntity.ok(getFlightResponse);
    }
    private GetFlightResponse createFlightResponse(Flight flight) {
        GetFlightResponse getFlightResponse = new GetFlightResponse();
        getFlightResponse.setId(flight.getId());
        getFlightResponse.setFlightName(flight.getFlightName());
        getFlightResponse.setDate(flight.getDate());
        getFlightResponse.setArrivalLocation(flight.getArrivalLocation());
        getFlightResponse.setDepartureLocation(flight.getDepartureLocation());
        // todo: will have to make a change here
        getFlightResponse.setNumberOfSeatsReserved(flight.getTotalSeatCapacity());
        getFlightResponse.setTicketBasePrice(flight.getTicketBasePrice());
        getFlightResponse.setReservationClosed(flight.getReservationClosed());

        return getFlightResponse;
    }
    @GetMapping
    public ResponseEntity<List<GetFlightResponse>> getAllConcerts() {
        List<Flight> flights = flightService.findAllFlights();
        // If there are no flights, then return a 204
        if (flights == null ||  flights.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        // Otherwise, convert the List of Flight objects into a List of GetFlightResponse and return it
        List<GetFlightResponse> response = new ArrayList<>();
        for (Flight flight : flights) {
            response.add(this.createFlightResponse(flight));
        }

        return ResponseEntity.ok(response);
    }
    // todo
    @PostMapping
    public ResponseEntity<GetFlightResponse> addNewFlight(@RequestBody GetFlightRequest getFlightRequest) {
        //System.out.println("POST /concerts " +" name " +concertCreateRequest.getName());

        Flight flight = new Flight(randomUUID().toString(),
                getFlightRequest.getName(),
                getFlightRequest.getDate().toString(),
                getFlightRequest.getDepartureLocation(),
                getFlightRequest.getArrivalLocation(),
                10,
                getFlightRequest.getTicketBasePrice(),
               false);
        flightService.addNewFlight(flight);

        GetFlightResponse flightResponse = createFlightResponse(flight);

        return ResponseEntity.created(URI.create("/flight/" + flightResponse.getId())).body(flightResponse);
    }
    @PutMapping
    public ResponseEntity<GetFlightResponse> updateConcert(@RequestBody FlightUpdateRequest flightUpdateRequest) {
        Flight flight = new Flight(flightUpdateRequest.getId(),
                flightUpdateRequest.getFlightName(),
                flightUpdateRequest.getDate(),
                flightUpdateRequest.getDepartureLocation(),
                flightUpdateRequest.getArrivalLocation(),
                flightUpdateRequest.getTotalSeatCapacity(),
                flightUpdateRequest.getTicketBasePrice(),
                flightUpdateRequest.getReservationClosed());
        GetFlightResponse getFlightResponse = createFlightResponse(flight);
        return ResponseEntity.ok(getFlightResponse);
    }
    @DeleteMapping("/{flightId}")
    public ResponseEntity deleteFlightById(@PathVariable("flightId") String flightId) {
        flightService.deleteFlight(flightId);
        //return ResponseEntity.noContent().build();
        return ResponseEntity.status((204)).build();
    }



}
