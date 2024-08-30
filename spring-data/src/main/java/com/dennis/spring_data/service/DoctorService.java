package com.dennis.spring_data.service;


import com.dennis.spring_data.model.Doctor;
import com.dennis.spring_data.repository.DoctorRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.List;
import java.util.Optional;

@Service
public class DoctorService {

    private final DoctorRepository doctorRepository;
    private final PlatformTransactionManager transactionManager;

    @Autowired
    public DoctorService(DoctorRepository doctorRepository, PlatformTransactionManager transactionManager) {
        this.doctorRepository = doctorRepository;
        this.transactionManager = transactionManager;
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "doctors", key = "#employeeID")
    public Optional<Doctor> getDoctorById(Long employeeID) {
        return doctorRepository.findById(employeeID);
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "doctors")
    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "doctorsBySpecialty", key = "#specialty")
    public List<Doctor> getDoctorsBySpecialty(String specialty) {
        return doctorRepository.findBySpecialty(specialty);
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "doctorsByLastName", key = "#lastName")
    public List<Doctor> getDoctorByLastName(String lastName) {
        return doctorRepository.findByLastName(lastName);
    }

    @Transactional
    @CacheEvict(value = "doctors", allEntries = true)
    public Doctor createDoctor(Doctor doctor) {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setName("CreateDoctorTransaction");
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);

        TransactionStatus status = transactionManager.getTransaction(def);

        try {
            Doctor savedDoctor = doctorRepository.save(doctor);
            transactionManager.commit(status);
            return savedDoctor;
        } catch (RuntimeException e) {
            transactionManager.rollback(status);
            throw e;
        }
    }

    @Transactional
    @CacheEvict(value = "doctors", allEntries = true)
    public Doctor updateDoctor(Long employeeID, Doctor doctorDetails) {
        Optional<Doctor> optionalDoctor = doctorRepository.findById(employeeID);
        if (optionalDoctor.isPresent()) {
            Doctor doctor = optionalDoctor.get();
            doctor.setFirstName(doctorDetails.getFirstName());
            doctor.setLastName(doctorDetails.getLastName());
            doctor.setAddress(doctorDetails.getAddress());
            doctor.setPhoneNumber(doctorDetails.getPhoneNumber());
            doctor.setSpecialty(doctorDetails.getSpecialty());
            return doctorRepository.save(doctor);
        } else {
            throw new RuntimeException("Doctor not found with id " + employeeID);
        }
    }

    @Transactional
    @CacheEvict(value = "doctors", allEntries = true)
    public void deleteDoctor(Long employeeID) {
        doctorRepository.deleteById(employeeID);
    }
}

