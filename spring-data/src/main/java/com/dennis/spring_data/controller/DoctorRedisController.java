package com.dennis.spring_data.controller;

import com.dennis.spring_data.model.DoctorRedis;
import com.dennis.spring_data.repository.DoctorRedisRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/redis")
public class DoctorRedisController {

    @Autowired
    private DoctorRedisRepo dao;

    @PostMapping("/doctor/create")
    public DoctorRedis save(@RequestBody DoctorRedis doctor) {
        return dao.save(doctor);
    }

    @GetMapping("/doctors")
    public List<DoctorRedis> getAllDoctors() {
        return dao.findAll();
    }

    @GetMapping("/doctor/{id}")
    public DoctorRedis findDoctor(@PathVariable int id) {
        return dao.findDoctorById(id);
    }
    @DeleteMapping("/doctor/delete/{id}")
    public String remove(@PathVariable int id)   {
        return dao.deleteDoctor(id);
    }

}
