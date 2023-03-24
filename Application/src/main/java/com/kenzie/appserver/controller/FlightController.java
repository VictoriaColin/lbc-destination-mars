package com.kenzie.appserver.controller;

import com.kenzie.appserver.service.FlightService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

@RequestMapping("/flight")
public class FlightController {

    private FlightService flightService;

    FlightController(FlightService flightService){
        this.flightService = flightService;
    }


}
