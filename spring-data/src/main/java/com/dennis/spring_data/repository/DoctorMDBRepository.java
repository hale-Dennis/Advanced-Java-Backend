package com.dennis.spring_data.repository;

import com.dennis.spring_data.model.DoctorMDB;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface DoctorMDBRepository extends MongoRepository<DoctorMDB, Integer> {
}
