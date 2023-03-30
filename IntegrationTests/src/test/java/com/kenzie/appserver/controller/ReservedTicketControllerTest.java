package com.kenzie.appserver.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.kenzie.appserver.IntegrationTest;

import com.kenzie.appserver.controller.model.ReservedTicketCreateRequest;
import com.kenzie.appserver.controller.model.ReservedTicketResponse;
import com.kenzie.appserver.service.FlightService;
import com.kenzie.appserver.service.ReservedTicketService;
import com.kenzie.appserver.service.model.Flight;
import com.kenzie.appserver.service.model.ReservedTicket;
import net.andreinc.mockneat.MockNeat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

//import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
//import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
//import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static java.util.UUID.randomUUID;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;


@IntegrationTest
public class ReservedTicketControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    FlightService flightService;

    @Autowired
    ReservedTicketService reservedTicketService;

    private final MockNeat mockNeat = MockNeat.threadLocal();

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    public void reserveTicket_creates_successfully() throws Exception {
        //GIVEN
        String flightId = UUID.randomUUID().toString();
        String flightName = mockNeat.strings().valStr();
        String date = LocalDate.now().toString();
        String departureLocation = "FL";
        String arrivalLocation = "MARS";
        Integer totalSeatCapacity = 10;
        Double ticketBasePrice = 100000.0;
        Boolean reservationClosed = false;

        Flight flight = new Flight(flightId,
                flightName,
                date,
                departureLocation,
                arrivalLocation,
                10,
                ticketBasePrice,
                reservationClosed);
        Flight persistedFlight = flightService.addNewFlight(flight);

        ReservedTicketCreateRequest reservedTicketCreateRequest = new ReservedTicketCreateRequest();
        reservedTicketCreateRequest.setFlightId(persistedFlight.getFlightId());

        mapper.registerModule(new JavaTimeModule());

        //WHEN
        ResultActions actions = mvc.perform(post("/reservedtickets")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(reservedTicketCreateRequest)))

                .andExpect(jsonPath("flightId").value(is(flightId)))
                .andExpect(jsonPath("ticketId").exists())
                .andExpect(jsonPath("dateOfReservation").exists())
                .andExpect(jsonPath("reservationClosed").value(is(false)))
                .andExpect(jsonPath("purchasedTicket").value(is(false)))
                .andExpect(status().is2xxSuccessful());

        String responseBody = actions.andReturn().getResponse().getContentAsString();
        ReservedTicketResponse reservedTicketResponse = mapper.readValue(responseBody, ReservedTicketResponse.class);
        //THEN
        assertThat(reservedTicketService.findByReservedTicketId(reservedTicketResponse.getTicketId()))
                .isNotNull()
                .as("The reserved ticket must be saved into the database");

    }

    @Test
    public void createReservedTicket_Flight_DoesNotExist() throws Exception {
        //GIVEN
        String flightId = UUID.randomUUID().toString();

        ReservedTicketCreateRequest reservedTicketCreateRequest = new ReservedTicketCreateRequest();
        reservedTicketCreateRequest.setFlightId(flightId);

        mapper.registerModule(new JavaTimeModule());
        //WHEN
        mvc.perform(post("/reservedtickets")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(reservedTicketCreateRequest)))
        //THEN
                .andExpect(status().isBadRequest());
    }


    @Test
    public void getAllReserveTicketsByFlight_Successful() throws Exception {
        //GIVEN
        String flightId = UUID.randomUUID().toString();
        String flightName = mockNeat.strings().valStr();
        String date = LocalDate.now().toString();
        String departureLocation = "FL";
        String arrivalLocation = "MARS";
        Integer totalSeatCapacity = 10;
        Double ticketBasePrice = 100000.0;
        Boolean reservationClosed = false;

        Flight flight = new Flight(flightId,
                flightName,
                date,
                departureLocation,
                arrivalLocation,
                10,
                ticketBasePrice,
                reservationClosed);
        Flight persistedFlight = flightService.addNewFlight(flight);

        ReservedTicket reservedTicket = new ReservedTicket(persistedFlight.getFlightId(), persistedFlight.getFlightName(), randomUUID().toString(), persistedFlight.getDepartureLocation(), persistedFlight.getArrivalLocation(), LocalDateTime.now().toString(), persistedFlight.getTotalSeatCapacity());
        ReservedTicket secondReservedTicket = new ReservedTicket(persistedFlight.getFlightId(), persistedFlight.getFlightName(), randomUUID().toString(), persistedFlight.getDepartureLocation(), persistedFlight.getArrivalLocation(), LocalDateTime.now().toString(), persistedFlight.getTotalSeatCapacity());

        reservedTicketService.reservedTicket(reservedTicket);
        reservedTicketService.reservedTicket(secondReservedTicket);

        mapper.registerModule(new JavaTimeModule());

        //WHEN
        ResultActions actions = mvc.perform(get("/reservedtickets/flight/{flightId}", persistedFlight.getFlightId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        //THEN
        String responseBody = actions.andReturn().getResponse().getContentAsString();

        List<ReservedTicketResponse> reservedTicketResponseList = mapper.readValue(responseBody, new TypeReference<List<ReservedTicketResponse>>() {
        });
        assertThat(reservedTicketResponseList.size()).isEqualTo(2);
    }

    @Test
    public void getAllReservedTicketsByFlight_NotFound() throws Exception {
        //GIVEN
        String flightId = UUID.randomUUID().toString();
        String flightName = mockNeat.strings().valStr();
        String date = LocalDate.now().toString();
        String departureLocation = "FL";
        String arrivalLocation = "MARS";
        Integer totalSeatCapacity = 10;
        Double ticketBasePrice = 100000.0;
        Boolean reservationClosed = false;

        Flight flight = new Flight(flightId,
                flightName,
                date,
                departureLocation,
                arrivalLocation,
                10,
                ticketBasePrice,
                reservationClosed);
        Flight persistedFlight = flightService.addNewFlight(flight);

        mapper.registerModule(new JavaTimeModule());

        //WHEN
        ResultActions actions = mvc.perform(get("/reservedtickets/flight/{flightId}", persistedFlight.getFlightId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                //THEN
                .andExpect(status().isNoContent());
    }


}
