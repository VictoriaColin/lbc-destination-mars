package com.kenzie.appserver.controller;

import com.kenzie.appserver.IntegrationTest;
import com.kenzie.appserver.controller.model.PurchasedTicketResponse;
import com.kenzie.appserver.service.FlightService;
import com.kenzie.appserver.service.PurchasedTicketService;
import com.kenzie.appserver.service.ReservedTicketService;
import com.kenzie.appserver.service.model.Flight;
import com.kenzie.appserver.service.model.ReservedTicket;
import net.andreinc.mockneat.MockNeat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import static java.util.UUID.randomUUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@IntegrationTest
public class PurchasedTicketControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    FlightService flightService;

    @Autowired
    ReservedTicketService reservedTicketService;

    @Autowired
    PurchasedTicketService purchasedTicketService;

    private final MockNeat mockNeat = MockNeat.threadLocal();

    private final ObjectMapper mapper = new ObjectMapper();


    @Test
    public void createPurchasedTicket_successfully() throws Exception {
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

        //need ro refactor to remove, or discuss changes with team
        Integer numberOfSeatsReserved = 1;

        ReservedTicket reservedTicket = new ReservedTicket(persistedFlight.getFlightId(),
                persistedFlight.getFlightName(),
                randomUUID().toString(),
                persistedFlight.getDepartureLocation(),
                persistedFlight.getArrivalLocation(),
                LocalDateTime.now().toString(),
                numberOfSeatsReserved);
        reservedTicket = reservedTicketService.reservedTicket(reservedTicket);

        //WHEN
        ResultActions actions = mvc.perform(post("/purchasedtickets")
                        .accept(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"ticketId\": \"" + reservedTicket.getTicketId() + "\"," +
                                "\"numberOfSeatsReserved\": " + numberOfSeatsReserved +
                                "}"
                        )
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        //THEN
        String responseBody = actions.andReturn().getResponse().getContentAsString();
        PurchasedTicketResponse purchasedTicketResponse = mapper.readValue(responseBody, PurchasedTicketResponse.class);
        assertThat(purchasedTicketResponse.getTicketId())
                .isEqualTo(reservedTicket.getTicketId())
                .as("The purchased ticket id should match the reservation id");
        assertThat(purchasedTicketResponse.getNumberOfSeatsReserved())
                .isEqualTo(reservedTicket.getNumberOfSeatsReserved())
                .as("The numberOfSeatsReservedShouldMatch");
        assertThat(purchasedTicketResponse.getDateOfPurchase())
                .isNotEmpty()
                .as("The purchase date should be populated");

        ReservedTicket closedReservedTicket = reservedTicketService.findByReservedTicketId(reservedTicket.getTicketId());
        assertThat(closedReservedTicket.getReservationClosed())
                .isEqualTo(true)
                .as("Reservation mus be closed after the ticket has been purchased");
    }

    @Test
    public void purchaseTicket_reservation_gets_closed() throws Exception {
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

        Integer numberOfSeatsReserved = 1;

        ReservedTicket reservedTicket = new ReservedTicket(persistedFlight.getFlightId(),
                persistedFlight.getFlightName(),
                randomUUID().toString(),
                persistedFlight.getDepartureLocation(),
                persistedFlight.getArrivalLocation(),
                LocalDateTime.now().toString(),
                numberOfSeatsReserved);
        reservedTicket = reservedTicketService.reservedTicket(reservedTicket);

        purchasedTicketService.purchaseTicket(reservedTicket.getTicketId(), 1);

        //WHEN
        ReservedTicket closedTicketReservation = reservedTicketService.findByReservedTicketId(reservedTicket.getTicketId());

        //THEN
        assertThat(closedTicketReservation.getReservationClosed())
                .isEqualTo(true)
                .as("Reservation must be closed after the ticket has been purchased");
        assertThat(closedTicketReservation.getPurchasedTicket())
                .isEqualTo(true)
                .as("The reservation should be classified as purchased");
    }

    @Test
    public void createPurchaseTicket_fails_reservation_already_closed() throws Exception {
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

        Integer numberOfSeatsReserved = 1;

        ReservedTicket reservedTicket = new ReservedTicket(persistedFlight.getFlightId(),
                persistedFlight.getFlightName(),
                randomUUID().toString(),
                persistedFlight.getDepartureLocation(),
                persistedFlight.getArrivalLocation(),
                LocalDateTime.now().toString(),
                numberOfSeatsReserved);
        reservedTicket = reservedTicketService.reservedTicket(reservedTicket);

        purchasedTicketService.purchaseTicket(reservedTicket.getTicketId(), numberOfSeatsReserved);

        //WHEN
        mvc.perform(post("/purchasedtickets")
                        .accept(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"ticketId\": \"" + reservedTicket.getTicketId() + "\"," +
                                "\"numberOfSeatsReserved\": " + 1 +
                                "}"
                        )
                        .contentType(MediaType.APPLICATION_JSON))
                //THEN
                .andExpect(status().is4xxClientError());
    }
}
