package com.kenzie.appserver.service;

import com.kenzie.appserver.service.model.ReservedTicket;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentLinkedQueue;

public class CloseReservationTask implements Runnable{
    private final Integer durationToPay;
    private final ConcurrentLinkedQueue<ReservedTicket> reservedTicketsQueue;
    private final ReservedTicketService reservedTicketService;

    //
    public CloseReservationTask(Integer durationToPay,
                                ReservedTicketService reservedTicketService,
                                ConcurrentLinkedQueue<ReservedTicket> reservedTicketsQueue) {
        this.durationToPay = durationToPay;
        this.reservedTicketService = reservedTicketService;
        this.reservedTicketsQueue = reservedTicketsQueue;
    }

    // step 1: Deque the ticket from the front/head of the queue
    // step 2: by id get the reservedTicket record from repo
    // step 3: get dateOfReservation from the reservedTicket
    // Step 4: get difference between the dateOfReservation and the current date now.
    // step 5: set the flag to true in ReservedTicketRecord[reservationClosed] after the time has expired
    // step 6: if the ticket is not purchased && duration is more than allowed
    // step 6.1: TRUE
    // close the ticket by changing reservationClosed to true and ticketPurchased to false.
    // pass the reserved ticket to reservedTicketService to update the reserved ticket
    // Step 6.2: FALSE - If the ticket has been purchased || is less than the duration
    // add back to the reservedTicketQueue
    @Override
    public void run() {
        // step 1: Deque the ticket from the top /head of the queue
        ReservedTicket reservedTicket = reservedTicketsQueue.poll();
        if (reservedTicket == null) {
            //System.out.println("*** Skipping the processing the null record ***");
            return;
        }
        String ticketId = reservedTicket.getTicketId();
        System.out.println(ticketId);
        // step 2: by id get the reservedTicket record from repo
        ReservedTicket reserveTicketId = null;
        try {
            reserveTicketId = reservedTicketService.findByReservedTicketId(ticketId);
        } catch (Exception e) {
            System.err.println("Exception in closeReservationTask thread run: " + e.getMessage() + " reservedTicket: " + reservedTicket.getTicketId() + " concertId " + reservedTicket.getFlightId());
        }
        // step 3: get dateOfReservation from the reservedTicket
        LocalDateTime dateOfReservation = null;
        try {
            dateOfReservation = LocalDateTime.parse(reserveTicketId.getDateOfReservation());
        }catch(Exception e){
            System.out.println("");
        }
        LocalDateTime currentDate = LocalDateTime.now();
        // Step 4: get difference between the dateOfReservation and the current date now.
        Duration duration = Duration.between(dateOfReservation, currentDate);
        int diff = Math.abs((int) duration.getSeconds());

        //System.out.println("difference between currentDate and dateOfReservation " + diff);
        // step 5: set the flag to true in ReservedTicketRecord[reservationClosed] after the time has expired
        // step 6: if the ticket is not purchased && duration is more than allowed
        // step 6.1: TRUE
        // close the ticket by changing reservationClosed to true and ticketPurchased to false.
        // pass the reserved ticket to reservedTicketService to update the reserved ticket
        //System.out.println("getTicketPurchased()? " + reserveTicketId.getTicketPurchased() +" diff " +diff +" durationToPay " +durationToPay);


        boolean isTicketNotPurchased = reserveTicketId.getPurchasedTicket() == null || !reserveTicketId.getPurchasedTicket();
        if (isTicketNotPurchased && diff > durationToPay) {
            //System.out.println("if...");


//            ReservedTicket reserveTicketRecord = new ReservedTicket(reserveTicketId.getFlightName(),
//                    ticketId,
//                    reserveTicketId.getDepartureLocation(),
//                    reserveTicketId.getDateOfReservation(),
//                    reserveTicketId.getArrivalLocation(),
//                    reserveTicketId.getDateOfReservation(),
//                    true,
//                    reservedTicket.getDateOfReservationClosed(),
//                    reserveTicketId.getNumberOfSeatsReserved(),
//                    false);

            ReservedTicket reserveTicketRecord = new ReservedTicket(reserveTicketId.getFlightId(),
                    reserveTicketId.getFlightName(),
                    ticketId,
                    reserveTicketId.getDepartureLocation(),
                    reserveTicketId.getArrivalLocation(),
                    reserveTicketId.getDateOfReservation(),
                    true,
                    LocalDateTime.now().toString(),
                    reserveTicketId.getNumberOfSeatsReserved(),
                    false);
            //System.out.println("reserveTicketRecord - getReservationClosed() " +reserveTicketRecord.getReservationClosed() +" getDateReservationClosed() " +reserveTicketRecord.getDateReservationClosed());
            reservedTicketService.updateReserveTicket(reserveTicketRecord);
            //System.out.println("persistedReservedTicket - getReservationClosed() " +persistedReservedTicket.getReservationClosed() +" getDateReservationClosed() " +persistedReservedTicket.getDateReservationClosed());
            // Step 6.2: FALSE - If the ticket has been purchased || is less than the duration
            // add back to the reservedTicketQueue
        } else if (reserveTicketId.getPurchasedTicket() || diff <= durationToPay) {
            //System.out.println("else...");
            reservedTicketsQueue.offer(reserveTicketId);
        } else {
            System.out.println("do nothing..");
        }


    }
}
