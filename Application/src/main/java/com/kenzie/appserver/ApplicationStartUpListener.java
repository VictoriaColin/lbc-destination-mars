package com.kenzie.appserver;


import com.kenzie.appserver.service.ReservedTicketService;
import com.kenzie.appserver.service.model.ReservedTicket;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;


@Component
public class ApplicationStartUpListener {

    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {
        // Perform any application start-up tasks
        ReservedTicketService reservedTicketService = event.getApplicationContext().getBean(ReservedTicketService.class);
        ConcurrentLinkedQueue queue = event.getApplicationContext().getBean(ConcurrentLinkedQueue.class);
        List<ReservedTicket> reservedTicketList = reservedTicketService.findAllUnclosedReservationTickets();
        queue.addAll(reservedTicketList);
    }
}
