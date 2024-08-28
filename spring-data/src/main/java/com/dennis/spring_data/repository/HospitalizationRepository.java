package com.dennis.spring_data.repository;


import com.dennis.spring_data.model.Hospitalization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HospitalizationRepository extends JpaRepository<Hospitalization, Long> {

}
