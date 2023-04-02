package com.kenzie.appserver.service;

import com.kenzie.appserver.repositories.SeatRepository;
import com.kenzie.appserver.repositories.model.SeatRecord;
import com.kenzie.appserver.service.model.Seat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SeatAvailabilityService {

    SeatRepository seatRepository;
    @Autowired
    public SeatAvailabilityService(SeatRepository seatRepository) {
        this.seatRepository = seatRepository;
    }


    public List<Seat> getSeats(String flightId){
       List<Seat> seats = new ArrayList<>();
//        Seat seat1 = new Seat(UUID.randomUUID().toString(),UUID.randomUUID().toString(),UUID.randomUUID().toString());
//        seats.add(seat1);
//        Seat seat2 = new Seat(UUID.randomUUID().toString(),UUID.randomUUID().toString(),UUID.randomUUID().toString());
//        seats.add(seat2);
        Iterable<SeatRecord> seatIterator = seatRepository.findAll();
        //List<SeatRecord> seatsByFlightId = seatRepository.(flightId);
        //System.out.println(seatsByFlightId);
        
        for(SeatRecord seatRecord: seatIterator ) {
            if (seatRecord.getTicketId() == null
                    && !seatRecord.getSeatReservationClosed()) {
                seats.add(new Seat(seatRecord.getFlightId(),
                        seatRecord.getSeatNumber(),
                        seatRecord.getTicketId(),
                        seatRecord.getSeatReservationClosed()));
            }
        }
        return seats;
    }

    public List<Seat> getAllSeats(String flightId){
        return getSeats(flightId);
    }
    public Seat addNewSeat(Seat seat) {
        SeatRecord seatRecord = new SeatRecord();
        seatRecord.setFlightId(seat.getFlightId());
        seatRecord.setSeatNumber(seat.getSeatNumber());
        seatRecord.setTicketId(seat.getTicketId());
        seatRecord.setSeatReservationClosed(seat.getSeatReservationClosed());
        seatRepository.save(seatRecord);
        return seat;
    }


    public void updateSeat(Seat seat){
        if(seatRepository.existsById(seat.getFlightId())){
            SeatRecord seatRecord = new SeatRecord();
            seatRecord.setFlightId(seat.getFlightId());
            seatRecord.setSeatNumber(seat.getSeatNumber());
            seatRecord.setTicketId(seat.getTicketId());
            seatRecord.setSeatReservationClosed(seat.getSeatReservationClosed());
            seatRepository.save(seatRecord);

        }
    }

}
