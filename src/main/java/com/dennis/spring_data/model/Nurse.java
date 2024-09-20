package com.dennis.spring_data.model;

import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Table(name = "nurses")
public class Nurse extends Employee {

    @Column(name = "rotation", nullable = false)
    private String rotation;

    @Column(name = "salary", nullable = false)
    private Double salary;

    @ManyToOne
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;
}

