package com.dennis.spring_data.controller;

import com.dennis.spring_data.model.Doctor;
import com.dennis.spring_data.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/v1/doctors")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;


    @PostMapping
    public HttpStatus createDoctor(@RequestBody Doctor doctor) {
        return doctorService.createDoctor(doctor);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Doctor> getDoctorById(@PathVariable Long id) {
        Optional<Doctor> doctor = doctorService.getDoctorById(id);
        return doctor.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<Doctor> getAllDoctors() {
        return doctorService.getAllDoctors();
    }

    @GetMapping("/specialty")
    public List<Doctor> getDoctorsBySpecialty(@RequestParam String specialty) {
        return doctorService.getDoctorsBySpecialty(specialty);
    }

    public List<Doctor> getDoctorsByLastName(@RequestParam String lastName) {
        return doctorService.getDoctorByLastName(lastName);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Doctor> updateDoctor(@PathVariable Long id, @RequestBody Doctor doctorDetails) {
        try {
            Doctor updatedDoctor = doctorService.updateDoctor(id, doctorDetails);
            return ResponseEntity.ok(updatedDoctor);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteDoctor(@PathVariable Long id) {
        doctorService.deleteDoctor(id);
        return ResponseEntity.noContent().build();
    }
}

