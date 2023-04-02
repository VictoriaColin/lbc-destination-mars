package com.kenzie.appserver.controller;

import com.kenzie.appserver.controller.model.*;
import com.kenzie.appserver.service.SeatAvailabilityService;
import com.kenzie.appserver.service.model.Flight;
import com.kenzie.appserver.service.model.Seat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static java.util.UUID.randomUUID;

@RestController
@RequestMapping("/seat")
public class SeatAvailabilityController {

    @Autowired
    private SeatAvailabilityService seatAvailabilityService;

    @GetMapping("/{flightId}")
    public ResponseEntity<List<SeatReservationResponse>> getAvailableSeats(@PathVariable String flightId) {

        List<Seat> seats = seatAvailabilityService.getSeats(flightId);
        // If there are no available, then return a 204
        if (seats == null || seats.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        // Otherwise, convert the List of Seat objects into a List of SeatResponse and return it
        List<SeatReservationResponse> response = new ArrayList<>();
        for (Seat seat : seats) {
            response.add(this.createSeatResponse(seat));
        }
        return ResponseEntity.ok(response);
    }


    @PostMapping
    public ResponseEntity<SeatReservationResponse> addNewSeat(@RequestBody AddSeatRequest request) {

        Seat seat = new Seat(randomUUID().toString(), request.getSeatNumber(), request.getTicketId(), false);
        seatAvailabilityService.addNewSeat(seat);

        SeatReservationResponse seatReservationResponse = createSeatResponse(seat);

        return ResponseEntity.created(URI.create("/seat/" + seatReservationResponse.getFlightId())).body(seatReservationResponse);
    }

    @PutMapping
    public ResponseEntity<SeatReservationResponse> updateSeat(@RequestBody SeatUpdateRequest seatUpdateRequest){
        Seat seat = new Seat(seatUpdateRequest.getFlightId(),
                seatUpdateRequest.getSeatNumber(),
                seatUpdateRequest.getTicketId(),
                false);

        SeatReservationResponse response = new SeatReservationResponse();
        response.setFlightId(seat.getFlightId());
        response.setSeatNumber(seat.getSeatNumber());
        response.setTicketId(seat.getTicketId());
        response.setSeatReservationClosed(true);

        return ResponseEntity.ok(response);
    }
    private SeatReservationResponse createSeatResponse(Seat seat) {
        SeatReservationResponse seatReservationResponse = new SeatReservationResponse();
        seatReservationResponse.setFlightId(seat.getFlightId());
        seatReservationResponse.setSeatNumber(seat.getSeatNumber());
        seatReservationResponse.setTicketId(seat.getTicketId());
        seatReservationResponse.setSeatReservationClosed(seat.getSeatReservationClosed());

        return seatReservationResponse;
    }
}




