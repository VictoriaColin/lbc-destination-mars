package com.kenzie.appserver;

import com.kenzie.appserver.service.AsynchronousService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Scheduler {
    // if we have repeated task a scheduler
    // every 200 ms it would wake up
    // and close any reservations not reserved

    // Create any asynchronous services

    @Autowired
    private AsynchronousService checkAsyncService;

    @Scheduled(fixedDelay = 2000)
    public void schedule() {
        // this piece of work is scheduled every 200 ms.
        checkAsyncService.executeAsynchronously();
    }

}
