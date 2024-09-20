package com.dennis.spring_data.repository;

import com.dennis.spring_data.model.Nurse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface NurseRepository extends JpaRepository<Nurse, Long> {
}
