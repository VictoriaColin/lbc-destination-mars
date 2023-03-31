package com.kenzie.appserver.service;

import com.kenzie.appserver.IntegrationTest;
import com.kenzie.appserver.service.model.Flight;
import com.kenzie.appserver.service.model.ReservedTicket;
import net.andreinc.mockneat.MockNeat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@IntegrationTest
public class CloseReservationTest {

    @Autowired
    FlightService flightService;

    @Autowired
    ReservedTicketService reservedTicketService;

    private final MockNeat mockNeat = MockNeat.threadLocal();

    @Test
    public void closeReservation_timeElapses() throws Exception {

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

        String ticketId = UUID.randomUUID().toString();

        ReservedTicket reservedTicket = new ReservedTicket(persistedFlight.getFlightId(),
                persistedFlight.getFlightName(),
                ticketId,
                persistedFlight.getDepartureLocation(),
                persistedFlight.getArrivalLocation(),
                LocalDateTime.now().minusHours(7).toString(),
                persistedFlight.getTotalSeatCapacity());

        reservedTicketService.reservedTicket(reservedTicket);

        Thread.sleep(3000);

        ReservedTicket persistedReservedTicket = reservedTicketService.findByReservedTicketId(reservedTicket.getTicketId());

        assertThat(persistedReservedTicket.getReservationClosed()).isTrue();
        assertThat(persistedReservedTicket.getDateOfReservationClosed()).isNotNull();
    }

    @Test
    public void closeReservation_notClosePurchaseTicket() throws Exception {
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

        String ticketId = UUID.randomUUID().toString();

        ReservedTicket reservedTicket = new ReservedTicket(persistedFlight.getFlightId(),
                persistedFlight.getFlightName(),
                ticketId,
                persistedFlight.getDepartureLocation(),
                persistedFlight.getArrivalLocation(),
                LocalDateTime.now().toString(),
                false,
                "",
                persistedFlight.getTotalSeatCapacity(),
                true);

        reservedTicketService.reservedTicket(reservedTicket);
        reservedTicketService.updateReserveTicket(reservedTicket);

        Thread.sleep(3000);

        ReservedTicket persistedReservedTicket = reservedTicketService.findByReservedTicketId(ticketId);

        assertThat(persistedReservedTicket.getReservationClosed()).isFalse();
        assertThat(persistedReservedTicket.getDateOfReservationClosed()).isNull();
    }


}
