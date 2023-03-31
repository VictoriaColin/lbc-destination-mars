package com.kenzie.appserver.service;

import com.kenzie.appserver.repositories.PurchasedTicketRepository;
import com.kenzie.appserver.repositories.model.PurchasedTicketRecord;
import com.kenzie.appserver.service.model.Flight;
import com.kenzie.appserver.service.FlightService;
import com.kenzie.appserver.service.model.PurchasedTicket;
import com.kenzie.appserver.service.model.ReservedTicket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class PurchasedTicketService {
    private PurchasedTicketRepository purchasedTicketRepository;
    private ReservedTicketService reservedTicketService;
    
    //adding in to get flight price
    //may just be easier to add in the pricePaid into the 
    //ReservedTicket model

    @Autowired
    private FlightService flightService;

    @Autowired
    public PurchasedTicketService(PurchasedTicketRepository purchasedTicketRepository, 
                                  ReservedTicketService reservedTicketService) {
        this.purchasedTicketRepository = purchasedTicketRepository;
        this.reservedTicketService = reservedTicketService;
    }

    
    public PurchasedTicket purchaseTicket(String reservedTicketId, Integer numberOfSeatsReserved) {

        ReservedTicket reservedTicket = reservedTicketService.findByReservedTicketId(reservedTicketId);
        Flight flightForReservedTicket = flightService.findByFlightId(reservedTicket.getFlightId());
        
        if (reservedTicket == null || (reservedTicket.getReservationClosed() != null && 
                reservedTicket.getReservationClosed() == true)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Reserved ticket is null or has closed");
        }
        PurchasedTicketRecord purchasedTicketRecord = new PurchasedTicketRecord();
        purchasedTicketRecord.setFlightId(reservedTicket.getFlightId());
        purchasedTicketRecord.setTicketId(reservedTicketId);
        purchasedTicketRecord.setDateOfPurchase(reservedTicket.getDateOfReservation());
        purchasedTicketRecord.setNumberOfSeatsReserved(numberOfSeatsReserved);
        purchasedTicketRecord.setPricePaid(flightForReservedTicket.getTicketBasePrice()); //need to review and possibly refactor ReservedTicket
        
        purchasedTicketRepository.save(purchasedTicketRecord);
        
        ReservedTicket updateReservedTicket = new ReservedTicket(reservedTicket.getFlightId(),
                reservedTicket.getFlightName(),
                reservedTicket.getTicketId(),
                reservedTicket.getDepartureLocation(),
                reservedTicket.getArrivalLocation(),
                reservedTicket.getDateOfReservation(),
                true,
                LocalDateTime.now().toString(),
                reservedTicket.getNumberOfSeatsReserved(),  //take from reservedTicket or from the method param? Check back
                true);
        
        reservedTicketService.updateReserveTicket(updateReservedTicket);
        
        PurchasedTicket purchasedTicket = new PurchasedTicket(purchasedTicketRecord.getFlightId(),
                purchasedTicketRecord.getTicketId(),
                purchasedTicketRecord.getDateOfPurchase(),
                purchasedTicketRecord.getNumberOfSeatsReserved(),
                purchasedTicketRecord.getPricePaid());
        
        return purchasedTicket;
    }

    public List<PurchasedTicket> findByFlightId(String flightId) {
        List<PurchasedTicketRecord> purchasedTicketRecords = purchasedTicketRepository.findByFlightId(flightId);
        List<PurchasedTicket> purchasedTickets = new ArrayList<>();

        for (PurchasedTicketRecord record : purchasedTicketRecords) {
            purchasedTickets.add(new PurchasedTicket(record.getFlightId(),
                    record.getTicketId(),
                    record.getDateOfPurchase(),
                    record.getNumberOfSeatsReserved(),
                    record.getPricePaid()));
        }

        return  purchasedTickets;
    }


   public void setFlightService(FlightService flightService){
        this.flightService = flightService;
    }




}
