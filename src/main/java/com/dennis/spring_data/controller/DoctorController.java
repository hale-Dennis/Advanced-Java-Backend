package com.dennis.spring_data.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/doctors")
public class DoctorController {

    @GetMapping()
    public String getDoctors() {
        return "Doctor List";
    }

}

