package com.kenzie.appserver.service;

import com.kenzie.appserver.repositories.ReservedTicketRepository;
import com.kenzie.appserver.repositories.model.ReservedTicketRecord;
import com.kenzie.appserver.service.model.Flight;
import com.kenzie.appserver.service.model.ReservedTicket;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentLinkedQueue;

@Service
public class ReservedTicketService {

    private FlightService flightService;
    private ReservedTicketRepository reservedTicketRepository;
    private final ConcurrentLinkedQueue<ReservedTicket> reservedTicketsQueue;

    public ReservedTicketService(FlightService flightService,
                                 ReservedTicketRepository reservedTicketRepository,
                                 ConcurrentLinkedQueue<ReservedTicket> reservedTicketsQueue) {
        this.flightService = flightService;
        this.reservedTicketRepository = reservedTicketRepository;
        this.reservedTicketsQueue = reservedTicketsQueue;
    }

    public List<ReservedTicket> findAllReservationTickets() {
        List<ReservedTicket> reservedTickets = new ArrayList<>();

        Iterable<ReservedTicketRecord> ticketRecords = reservedTicketRepository.findAll();

        for (ReservedTicketRecord record : ticketRecords) {
            reservedTickets.add(new ReservedTicket(record.getFlightId(),
                    record.getFlightName(),
                    record.getTicketId(),
                    record.getDepartureLocation(),
                    record.getArrivalLocation(),
                    record.getDateOfReservation(),
                    record.getReservationClosed(),
                    record.getDateOfReservationClosed(),
                    record.getNumberOfSeatsReserved(),
                    record.getPurchasedTicket()));
        }
        return reservedTickets;
    }

    public List<ReservedTicket> findAllUnclosedReservationTickets() {
        List<ReservedTicket> allReservedTickets = findAllReservationTickets();
        List<ReservedTicket> unclosedReservations = new ArrayList<>();

        for (ReservedTicket reservedTicket : allReservedTickets) {
            if ((reservedTicket.getPurchasedTicket() == null || !reservedTicket.getPurchasedTicket()) &&
                    (reservedTicket.getReservationClosed() == null || !reservedTicket.getReservationClosed())) {
                unclosedReservations.add(reservedTicket);
            }
        }
        return unclosedReservations;
    }

    public ReservedTicket reservedTicket(ReservedTicket reservedTicket) {
        Flight flightId = flightService.findByFlightId(reservedTicket.getFlightId());

        if (flightId == null || flightId.getReservationClosed()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Flight does not exist or reservations are closed");
        }

        ReservedTicketRecord reservedTicketRecord = new ReservedTicketRecord();
        reservedTicketRecord.setFlightId(reservedTicket.getFlightId());
        reservedTicketRecord.setTicketId(reservedTicket.getTicketId());
        reservedTicketRecord.setDateOfReservation(reservedTicket.getDateOfReservation());

        reservedTicketRepository.save(reservedTicketRecord);
        reservedTicketsQueue.add(reservedTicket);

        return reservedTicket;
    }

    public ReservedTicket findByReservedTicketId(String reserveTicketId) {
        Optional<ReservedTicketRecord> ticketRecordOptional = reservedTicketRepository.findById(reserveTicketId);
        if (ticketRecordOptional.isPresent()) {
            ReservedTicketRecord record = ticketRecordOptional.get();
            return new ReservedTicket(record.getFlightId(),
                    record.getFlightName(),
                    record.getTicketId(),
                    record.getDepartureLocation(),
                    record.getArrivalLocation(),
                    record.getDateOfReservation(),
                    record.getReservationClosed(),
                    record.getDateOfReservationClosed(),
                    record.getNumberOfSeatsReserved(),
                    record.getPurchasedTicket());
        } else {
            return null;
        }
    }

    public List<ReservedTicket> findByFlightId(String flightId) {
        List<ReservedTicket> reservedTicketList = new ArrayList<>();
        List<ReservedTicketRecord> reservedTicketRecordList = reservedTicketRepository.findByFlightId(flightId);

        for (ReservedTicketRecord reservedTicket : reservedTicketRecordList) {
            ReservedTicket newReservedTicket = new ReservedTicket(reservedTicket.getFlightId(),
                    reservedTicket.getFlightName(),
                    reservedTicket.getTicketId(),
                    reservedTicket.getDepartureLocation(),
                    reservedTicket.getArrivalLocation(),
                    reservedTicket.getDateOfReservation(),
                    reservedTicket.getReservationClosed(),
                    reservedTicket.getDateOfReservationClosed(),
                    reservedTicket.getNumberOfSeatsReserved(),
                    reservedTicket.getPurchasedTicket());

            reservedTicketList.add(newReservedTicket);
        }
        return reservedTicketList;
    }

    public ReservedTicket updateReserveTicket(ReservedTicket reservedTicket) {
        ReservedTicketRecord reservedTicketRecord = new ReservedTicketRecord();
        reservedTicketRecord.setFlightId(reservedTicket.getFlightId());
        reservedTicketRecord.setFlightName(reservedTicket.getFlightName());
        reservedTicketRecord.setTicketId(reservedTicket.getTicketId());
        reservedTicketRecord.setDepartureLocation(reservedTicket.getDepartureLocation());
        reservedTicketRecord.setArrivalLocation(reservedTicket.getArrivalLocation());
        reservedTicketRecord.setDateOfReservation(reservedTicket.getDateOfReservation());
        reservedTicketRecord.setReservationClosed(reservedTicket.getReservationClosed());
        reservedTicketRecord.setDateOfReservationClosed(reservedTicket.getDateOfReservationClosed());
        reservedTicketRecord.setNumberOfSeatsReserved(reservedTicket.getNumberOfSeatsReserved());
        reservedTicketRecord.setPurchasedTicket(reservedTicket.getPurchasedTicket());

        reservedTicketRepository.save(reservedTicketRecord);
        return reservedTicket;
    }


}
