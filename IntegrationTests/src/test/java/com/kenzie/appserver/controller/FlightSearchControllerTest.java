package com.kenzie.appserver.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.kenzie.appserver.IntegrationTest;
import com.kenzie.appserver.controller.model.AddFlightRequest;
import com.kenzie.appserver.controller.model.FlightUpdateRequest;
import com.kenzie.appserver.service.FlightService;
import com.kenzie.appserver.service.model.Flight;
import net.andreinc.mockneat.MockNeat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@IntegrationTest
public class FlightSearchControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    FlightService flightService;

    private final MockNeat mockNeat = MockNeat.threadLocal();

    private final ObjectMapper mapper = new ObjectMapper();

  
    @Test
    // POST
    public void addFlight_AdditionSuccessful() throws Exception {
        // GIVEN
        String name = mockNeat.strings().valStr();
        String departureLocation = "CA";
        String arrivalLocation = "MARS";
        String date = LocalDate.now().toString();
        Double ticketBasePrice = 100000.0;

        AddFlightRequest addFlightRequest = new AddFlightRequest();
        addFlightRequest.setName(name);
        addFlightRequest.setDepartureLocation(departureLocation);
        addFlightRequest.setArrivalLocation(arrivalLocation);
        addFlightRequest.setDate(LocalDate.now());
        addFlightRequest.setTicketBasePrice(ticketBasePrice);

        mapper.registerModule(new JavaTimeModule());

        // WHEN
        mvc.perform(post("/flight")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(addFlightRequest)))
                // THEN
                .andExpect(jsonPath("flightName")
                        .value(is(name)))
                .andExpect(jsonPath("departureLocation")
                        .value(is(departureLocation)))
                .andExpect(jsonPath("arrivalLocation")
                        .value(is(arrivalLocation)))
                .andExpect(jsonPath("date")
                        .value(is(date)))
                .andExpect(jsonPath("ticketBasePrice")
                        .value(is(ticketBasePrice)))
                .andExpect(status().isCreated());
    }

    @Test
    //GET
    public void getFlight_FlightExists() throws Exception {
        // GIVEN
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
        Flight persistedFlight= flightService.addNewFlight(flight);

        // WHEN
        mvc.perform(get("/flight/{flightId}", persistedFlight.getFlightId())
                        .accept(MediaType.APPLICATION_JSON))
                // THEN
                .andExpect(jsonPath("flightId")
                        .value(is(flightId)))
                .andExpect(jsonPath("flightName")
                        .value(is(flightName)))
                .andExpect(jsonPath("date")
                        .value(is(date)))
                .andExpect(jsonPath("departureLocation")
                        .value(is(departureLocation)))
                .andExpect(jsonPath("arrivalLocation")
                        .value(is(arrivalLocation)))
                .andExpect(jsonPath("ticketBasePrice")
                        .value(is(ticketBasePrice)))
                .andExpect(jsonPath("reservationClosed")
                        .value(is(false)))
                .andExpect(status().isOk());
    }
    @Test
    //GET
    public void getFlight_FlightDoesNotExist() throws Exception {
        // GIVEN
        String flightId = UUID.randomUUID().toString();
        // WHEN
        mvc.perform(get("/flight/{flightId}", flightId)
                        .accept(MediaType.APPLICATION_JSON))
                // THEN
                .andExpect(status().isNotFound());
    }
    @Test
    //PUT
    public void updateFlight_PutSuccessful() throws Exception {
        // GIVEN
        String flightId = UUID.randomUUID().toString();
        String flightName = mockNeat.strings().valStr();
        String date = LocalDate.now().toString();
        String departureLocation = "FL";
        String arrivalLocation = "MARS";
        Double ticketBasePrice = 100000.0;
        Boolean reservationClosed = true;

        Flight flight = new Flight(flightId,
                flightName,
                date,
                departureLocation,
                arrivalLocation,
                10,
                ticketBasePrice,
                reservationClosed);

        Flight persistedFlight = flightService.addNewFlight(flight);

        String newName = mockNeat.strings().valStr();
        String newDepartureLocation = "CA";
        Double newTicketBasePrice = 1000000.0;

        FlightUpdateRequest flightUpdateRequest = new FlightUpdateRequest();
        flightUpdateRequest.setFlightId(flightId);
        flightUpdateRequest.setDate(LocalDate.now());
        flightUpdateRequest.setDepartureLocation(newDepartureLocation);
        flightUpdateRequest.setArrivalLocation(arrivalLocation);
        flightUpdateRequest.setTotalSeatCapacity(10);
        flightUpdateRequest.setTicketBasePrice(newTicketBasePrice);
        flightUpdateRequest.setReservationClosed(true);

        mapper.registerModule(new JavaTimeModule());

        // WHEN
        mvc.perform(put("/flight")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(flightUpdateRequest)))
                // THEN
                .andExpect(jsonPath("flightId")
                        .exists())
                .andExpect(jsonPath("date")
                        .value(is(date)))
                .andExpect(jsonPath("departureLocation")
                        .value(is(newDepartureLocation)))
                .andExpect(jsonPath("arrivalLocation")
                        .value(is(arrivalLocation)))
                .andExpect(jsonPath("ticketBasePrice")
                        .value(is(newTicketBasePrice)))
                .andExpect(jsonPath("reservationClosed")
                        .value(is(true)))
                .andExpect(status().isOk());
    }
    @Test
    //DELETE
    public void deleteFlight_DeleteSuccessful() throws Exception {
        // GIVEN
        String flightId = UUID.randomUUID().toString();
        String flightName = mockNeat.strings().valStr();
        String date = LocalDate.now().toString();
        String departureLocation = "FL";
        String arrivalLocation = "MARS";
        //Integer totalSeatCapacity = 10;
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

        // WHEN
        mvc.perform(delete("/flight/{flightId}", persistedFlight.getFlightId())
                        .accept(MediaType.APPLICATION_JSON))
                // THEN
                .andExpect(status().isNoContent());
        assertThat(flightService.findByFlightId(flightId)).isNull();
    }

}
