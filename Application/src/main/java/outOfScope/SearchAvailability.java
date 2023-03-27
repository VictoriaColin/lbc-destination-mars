package outOfScope;

import com.kenzie.appserver.repositories.FlightRepository;
import com.kenzie.appserver.repositories.model.FlightRecord;
import com.kenzie.appserver.service.FlightService;
import com.kenzie.appserver.service.model.Flight;

import java.time.LocalDate;
import java.util.*;

public class SearchAvailability {
    FlightService flightService;
    FlightRepository flightRepository;
    String startDate = "2014-05-01";
    String endDate = "2014-05-10";


    public List<Flight> findAllConcerts() {
        List<Flight> flights = new ArrayList<>();


        Iterable<FlightRecord> flightIterator = flightRepository.findAll();
        for (FlightRecord record : flightIterator) {
            String date = record.getDate();

            if(isDateBetweenTwoDate(startDate, endDate, date)) {
                flights.add(new Flight(record.getId(),
                        record.getFlightName(),
                        record.getDate(),
                        record.getDepartureLocation(),
                        record.getArrivalLocation(),
                        record.getTotalSeatCapacity(),
                        record.getTicketBasePrice(),
                        record.getReservationClosed()));
            }
        }

        return flights;
    }

    public boolean isDateBetweenTwoDate(String startDate, String endDate, String currentDate) {
        //String startDate = "2014-05-01";
        //String endDate = "2014-05-10";
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);
        LocalDate current = LocalDate.parse(currentDate);// date in question

        return current.compareTo(start) >= 0 && current.compareTo(end) <= 0;
    }
}
