package com.kenzie.appserver.controller;

import com.kenzie.appserver.controller.model.PurchasedTicketCreateRequest;
import com.kenzie.appserver.controller.model.PurchasedTicketResponse;
import com.kenzie.appserver.service.CreditCardService;
import com.kenzie.appserver.service.PurchasedTicketService;
import com.kenzie.appserver.service.ReservedTicketService;
import com.kenzie.appserver.service.model.PurchasedTicket;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/purchasedtickets")
public class PurchasedTicketController {
    
    private PurchasedTicketService purchasedTicketService;
    private ReservedTicketService reservedTicketService;
    private CreditCardService creditCardService;

    public PurchasedTicketController(PurchasedTicketService purchasedTicketService, 
                                     ReservedTicketService reservedTicketService) {
        this.purchasedTicketService = purchasedTicketService;
        this.reservedTicketService = reservedTicketService;
    }
    
    @PostMapping
    public ResponseEntity<PurchasedTicketResponse> purchaseTicket(
            @RequestBody PurchasedTicketCreateRequest purchasedTicketCreateRequest) {

        // Validate Credit Card
//         boolean cardValid = creditCardService.validate(purchasedTicketCreateRequest.getCreditCard());
//         if (!cardValid) return ResponseEntity.ok("failedPurchasedTicketResponse");

        PurchasedTicket purchasedTicket = purchasedTicketService.purchaseTicket(purchasedTicketCreateRequest.getTicketId(),
                purchasedTicketCreateRequest.getNumberOfSeatsReserved());
        
        PurchasedTicketResponse purchasedTicketResponse = createPurchasedTicketResponse(purchasedTicket);
        
        return ResponseEntity.ok(purchasedTicketResponse);
    }
    
    @GetMapping("/flight/{flightId}")
    public ResponseEntity<List<PurchasedTicketResponse>> getAllPurchasedTicketsByFlightId(
            @PathVariable("flightId") String flightId) {
        List<PurchasedTicket> purchasedTicketsList = purchasedTicketService.findByFlightId(flightId);
        if (purchasedTicketsList == null || purchasedTicketsList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        List<PurchasedTicketResponse> tickets = new ArrayList<>();
        
        for (PurchasedTicket purchasedTicket : purchasedTicketsList) {
            tickets.add(createPurchasedTicketResponse(purchasedTicket));
        }
        return ResponseEntity.ok(tickets);
    }

    private PurchasedTicketResponse createPurchasedTicketResponse(PurchasedTicket purchasedTicket) {
        PurchasedTicketResponse purchasedTicketResponse = new PurchasedTicketResponse();
        purchasedTicketResponse.setFlightName(purchasedTicketResponse.getFlightName());
        purchasedTicketResponse.setTicketId(purchasedTicket.getTicketId());
        purchasedTicketResponse.setDateOfPurchase(purchasedTicket.getDateOfPurchase());
        purchasedTicketResponse.setNumberOfSeatsReserved(purchasedTicket.getNumberOfSeatsReserved());
        purchasedTicketResponse.setPricePaid(purchasedTicket.getPricePaid());

        return purchasedTicketResponse;
    }
}
