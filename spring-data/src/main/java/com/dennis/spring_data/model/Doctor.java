package com.dennis.spring_data.model;

import jakarta.persistence.*;
import lombok.Data;



@Data
@Entity
@Table(name = "doctors")
public class Doctor extends Employee {

    @Column(name = "specialty", nullable = false)
    private String specialty;

}
