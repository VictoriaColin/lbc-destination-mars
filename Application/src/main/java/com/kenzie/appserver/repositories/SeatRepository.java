package com.kenzie.appserver.repositories;

import com.kenzie.appserver.repositories.model.SeatRecord;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

@EnableScan
public interface SeatRepository extends CrudRepository<SeatRecord, String> {


}
