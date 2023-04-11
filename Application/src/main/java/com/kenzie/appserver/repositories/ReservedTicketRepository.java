package com.kenzie.appserver.repositories;

import com.kenzie.appserver.repositories.model.ReservedTicketRecord;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

@EnableScan
public interface ReservedTicketRepository extends CrudRepository<ReservedTicketRecord, String> {
    List<ReservedTicketRecord> findByFlightId(String flightId);
}
