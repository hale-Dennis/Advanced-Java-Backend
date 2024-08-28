package com.dennis.spring_data.service;


import com.dennis.spring_data.model.Doctor;
import com.dennis.spring_data.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;


    public Doctor createDoctor(Doctor doctor) {
        return doctorRepository.save(doctor);
    }


    public Optional<Doctor> getDoctorById(Long id) {
        return doctorRepository.findById(id);
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


    public Doctor updateDoctor(Long id, Doctor doctorDetails) {
        Optional<Doctor> optionalDoctor = doctorRepository.findById(id);
        if (optionalDoctor.isPresent()) {
            Doctor doctor = optionalDoctor.get();
            doctor.setFirstName(doctorDetails.getFirstName());
            doctor.setLastName(doctorDetails.getLastName());
            doctor.setAddress(doctorDetails.getAddress());
            doctor.setPhoneNumber(doctorDetails.getPhoneNumber());
            doctor.setSpecialty(doctorDetails.getSpecialty());
            return doctorRepository.save(doctor);
        } else {
            throw new RuntimeException("Doctor not found with id " + id);
        }
    }


    public void deleteDoctor(Long id) {
        doctorRepository.deleteById(id);
    }
}

