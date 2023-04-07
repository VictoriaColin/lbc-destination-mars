package com.kenzie.appserver.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.kenzie.appserver.IntegrationTest;
import com.kenzie.appserver.controller.model.AddSeatRequest;
import com.kenzie.appserver.controller.model.ReservedTicketResponse;
import com.kenzie.appserver.controller.model.SeatReservationResponse;
import com.kenzie.appserver.service.FlightService;
import com.kenzie.appserver.service.SeatAvailabilityService;
import com.kenzie.appserver.service.model.Seat;
import net.andreinc.mockneat.MockNeat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@IntegrationTest
public class SeatAvailabilityControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    SeatAvailabilityService seatAvailabilityService;

    private final MockNeat mockNeat = MockNeat.threadLocal();

    private final ObjectMapper mapper = new ObjectMapper();


    @Test
    public void addSeat_AdditionSuccessful() throws Exception {
        //GIVEN
        String flightName = mockNeat.strings().valStr();
        String flightId = UUID.randomUUID().toString();
        String seatNumber = "22";
        String ticketId = UUID.randomUUID().toString();
        boolean seatReservationClosed = false;
        Seat seat = new Seat(flightId,seatNumber,ticketId,seatReservationClosed);

        seatAvailabilityService.addNewSeat(seat);

        AddSeatRequest addSeatRequest = new AddSeatRequest();
        addSeatRequest.setFlightName(flightName);
        addSeatRequest.setFlightId(flightId);
        addSeatRequest.setSeatNumber(seatNumber);
        addSeatRequest.setTicketId(ticketId);
        addSeatRequest.setSeatReservationClosed(seatReservationClosed);


        mvc.perform(post("/seat")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(addSeatRequest)))
                .andExpect(jsonPath("flightId")
                        .exists())
                .andExpect(jsonPath("seatNumber")
                        .value(is(seatNumber)))
                .andExpect(jsonPath("ticketId")
                        .value(is(ticketId)));
    }

    @Test
    public void getAvailableSeats_SeatsExist_isSuccessful() throws Exception {
        //GIVEN
        String flightName = mockNeat.strings().valStr();
        String flightId = "SPX1003";
        String seatNumber = "22";
        String ticketId = UUID.randomUUID().toString();
        boolean seatReservationClosed = false;

        Seat seat1 = new Seat(flightId,seatNumber,ticketId,seatReservationClosed);
        Seat seat2 = new Seat(flightId,"33",ticketId,seatReservationClosed);
        seatAvailabilityService.addNewSeat(seat1);
        seatAvailabilityService.addNewSeat(seat2);

        ResultActions actions = mvc.perform(get("/seat/{flightId}", flightId)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());

    }






}
