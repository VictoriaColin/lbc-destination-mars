package com.kenzie.appserver.repositories;

import com.kenzie.appserver.repositories.model.PurchasedTicketRecord;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

@EnableScan
public interface PurchasedTicketRepository extends CrudRepository<PurchasedTicketRecord, String> {
    List<PurchasedTicketRecord> findByFlightId(String flightId);
}
