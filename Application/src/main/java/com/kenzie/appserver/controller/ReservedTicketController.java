package com.kenzie.appserver.controller;

import com.kenzie.appserver.controller.model.ReservedTicketCreateRequest;
import com.kenzie.appserver.controller.model.ReservedTicketResponse;
import com.kenzie.appserver.service.ReservedTicketService;
import com.kenzie.appserver.service.model.ReservedTicket;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static java.util.UUID.randomUUID;

@RestController
@RequestMapping("/reservedtickets")
public class ReservedTicketController {

    private ReservedTicketService reservedTicketService;

    ReservedTicketController(ReservedTicketService reservedTicketService) {
        this.reservedTicketService = reservedTicketService;
    }

    @PostMapping
    public ResponseEntity<ReservedTicketResponse> reserveTicket(
            @RequestBody ReservedTicketCreateRequest reservedTicketCreateRequest) {
        ReservedTicket reservedTicket = new ReservedTicket(reservedTicketCreateRequest.getFlightId(),
                reservedTicketCreateRequest.getFlightName(),
                randomUUID().toString(),
                reservedTicketCreateRequest.getDepartureLocation(),
                reservedTicketCreateRequest.getArrivalLocation(),
                LocalDateTime.now().toString(),
                reservedTicketCreateRequest.getNumberOfSeatsReserved());
        reservedTicketService.reservedTicket(reservedTicket);

        ReservedTicketResponse reservedTicketResponse = createReservedTicketResponse(reservedTicket);

        return ResponseEntity.ok(reservedTicketResponse);
    }

    private ReservedTicketResponse createReservedTicketResponse(ReservedTicket reservedTicket) {
        ReservedTicketResponse response = new ReservedTicketResponse();
        response.setFlightId(reservedTicket.getFlightId());
        response.setFlightName(reservedTicket.getFlightName());
        response.setTicketId(reservedTicket.getTicketId());
        response.setDepartureLocation(reservedTicket.getDepartureLocation());
        response.setArrivalLocation(reservedTicket.getArrivalLocation());
        response.setDateOfReservation(reservedTicket.getDateOfReservation());
        response.setReservationClosed(reservedTicket.getReservationClosed());
        response.setDateOfReservationClosed(reservedTicket.getDateOfReservationClosed());
        response.setNumberOfSeatsReserved(reservedTicket.getNumberOfSeatsReserved());
        response.setPurchasedTicket(reservedTicket.getPurchasedTicket());

        return response;
    }

    @GetMapping("/flight/{flightId}")
    public ResponseEntity<List<ReservedTicketResponse>> getAllReservedTicketsByFlightId(
            @PathVariable("flightId") String flightId) {

        List<ReservedTicketResponse> reservedTicketResponseList = new ArrayList<>();
        List<ReservedTicket> reservedTicketList = reservedTicketService.findByFlightId(flightId);

        if (reservedTicketList == null || reservedTicketList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        for (ReservedTicket reservedTicket : reservedTicketList) {
            reservedTicketResponseList.add(this.createReservedTicketResponse(reservedTicket));
        }
        return ResponseEntity.ok(reservedTicketResponseList);
    }

}
