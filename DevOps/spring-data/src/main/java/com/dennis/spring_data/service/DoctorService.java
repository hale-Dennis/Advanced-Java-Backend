package com.dennis.spring_data.service;


import com.dennis.spring_data.model.Doctor;
import com.dennis.spring_data.repository.DoctorRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.List;
import java.util.Optional;

@Service
public class DoctorService {

    private final DoctorRepository doctorRepository;

    @Autowired
    public DoctorService(DoctorRepository doctorRepository, PlatformTransactionManager transactionManager) {
        this.doctorRepository = doctorRepository;
    }

    public Optional<Doctor> getDoctorById(Long employeeID) {
        return doctorRepository.findById(employeeID);
    }

    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }


    public List<Doctor> getDoctorsBySpecialty(String specialty) {
        return doctorRepository.findBySpecialty(specialty);
    }

    public List<Doctor> getDoctorByLastName(String lastName) {
        return doctorRepository.findByLastName(lastName);
    }

    public HttpStatus createDoctor(Doctor doctor) {
        doctorRepository.save(doctor);
        return HttpStatus.CREATED;
    }


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


    public void deleteDoctor(Long employeeID) {
        doctorRepository.deleteById(employeeID);
    }
}

