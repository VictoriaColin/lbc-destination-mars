package com.kenzie.appserver.service;

import com.kenzie.appserver.repositories.ReservedTicketRepository;
import com.kenzie.appserver.repositories.model.ReservedTicketRecord;
import com.kenzie.appserver.service.model.ReservedTicket;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

@Service
public class ReservedTicketService {

//    private FlightService flightService;
//    private ReservedTicketRepository reservedTicketRepository;
//    private final ConcurrentLinkedQueue<ReservedTicket> reservedTicketsQueue;
//
//    public ReservedTicketService(FlightService flightService,
//                                 ReservedTicketRepository reservedTicketRepository,
//                                 ConcurrentLinkedQueue<ReservedTicket> reservedTicketsQueue) {
//        this.flightService = flightService;
//        this.reservedTicketRepository = reservedTicketRepository;
//        this.reservedTicketsQueue = reservedTicketsQueue;
//    }
//
//    public List<ReservedTicket> findAllReservationTickets() {
//        List<ReservedTicket> reservedTickets = new ArrayList<>();
//
//        Iterable<ReservedTicketRecord> ticketRecords = reservedTicketRepository.findAll();
//
//        for (ReservedTicketRecord record : ticketRecords) {
//            reservedTickets.add(new ReservedTicket(record.getFlightId(),
//                    record.getFlightName(),
//                    record.getTicketId(),
//                    record.getDepartureLocation(),
//                    record.getArrivalLocation(),
//                    record.getDateOfReservation(),
//                    record.getReservationClosed(),
//                    record.getDateOfReservationClosed(),
//                    record.getNumberOfSeatsReserved(),
//                    record.getPurchasedTicket()));
//        }
//        return reservedTickets;
//    }
//
//    public List<ReservedTicket> findAllUnclosedReservationTickets() {
//        List<ReservedTicket> allReservedTickets = findAllReservationTickets();
//        List<ReservedTicket> unclosedReservations = new ArrayList<>();
//
//        for (ReservedTicket reservedTicket : allReservedTickets) {
//            if ((reservedTicket.getPurchasedTicket() == null || !reservedTicket.getPurchasedTicket()) &&
//                    (reservedTicket.getReservationClosed() == null || !reservedTicket.getReservationClosed())) {
//                unclosedReservations.add(reservedTicket);
//            }
//        }
//        return unclosedReservations;
//    }




}
