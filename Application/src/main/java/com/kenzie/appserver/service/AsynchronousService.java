package com.kenzie.appserver.service;

import com.kenzie.appserver.service.model.ReservedTicket;
import com.kenzie.ata.ExcludeFromJacocoGeneratedReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentLinkedQueue;

@Service
public class AsynchronousService {
    // Sourcing it from resources
    // will be set to 60
    @Value("${time.to.purchase.ticket}")
    private Integer durationToPay;

    @Autowired
    private TaskExecutor executorService;

    @Autowired
    private ApplicationContext applicationContext;

    // running an asynchronous task to CloseReservationTask
    @ExcludeFromJacocoGeneratedReport
    public void executeAsynchronously() {
        //System.out.println("***start***");
        ReservedTicketService reservedTicketService = applicationContext.getBean(ReservedTicketService.class);
        ConcurrentLinkedQueue<ReservedTicket> queue = applicationContext.getBean(ConcurrentLinkedQueue.class);
//        CloseReservationTask task = new CloseReservationTask(durationToPay, reservedTicketService, queue);
//        executorService.execute(task);
        executorService.execute(new CloseReservationTask(durationToPay, reservedTicketService, queue));
        //System.out.println("****end***");
    }
}
