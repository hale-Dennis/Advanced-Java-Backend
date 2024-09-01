package com.dennis.spring_data.controller;


import com.dennis.spring_data.model.DoctorMDB;
import com.dennis.spring_data.repository.DoctorMDBRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DoctorMDBController {

    @Autowired
    private DoctorMDBRepository doctorMDBRepository;

    @PostMapping("/doctor/create")
    public void insertDoctorMDB(@RequestBody DoctorMDB doctor) {
        doctorMDBRepository.save(doctor);
    }

    @GetMapping("/doctors")
    public List<DoctorMDB> getAllDoctorMDB() {
        return doctorMDBRepository.findAll();
    }

    @GetMapping("/doctor/{id}")
    public DoctorMDB getDoctorMDBById(@PathVariable Integer id) {
        return doctorMDBRepository.findById(id).orElse(null);
    }

    @PutMapping("/doctor/update")
    public void updateDoctorMDB(@RequestBody DoctorMDB doctor) {
        DoctorMDB outdatedDoctor = doctorMDBRepository.findById(doctor.getId()).orElse(null);
        if (outdatedDoctor != null) {
            outdatedDoctor.setEmail(doctor.getEmail());
            outdatedDoctor.setPhone(doctor.getPhone());
            outdatedDoctor.setFirstName(doctor.getFirstName());
            outdatedDoctor.setLastName(doctor.getLastName());
            doctorMDBRepository.save(outdatedDoctor);
        }
    }

    @DeleteMapping("/doctor/delete/{id}")
    public void deleteDoctorMDB(@PathVariable Integer id) {
        if(doctorMDBRepository.existsById(id)) {
            doctorMDBRepository.deleteById(id);
        }
    }
}
