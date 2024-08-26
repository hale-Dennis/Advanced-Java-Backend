package com.dennis.spring_data.model;


import lombok.Data;
import jakarta.persistence.*;

import java.util.List;

@Data
@Entity
@Table(name = "departments")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long departmentID;

    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "building", nullable = false)
    private String building;

    @OneToOne
    @JoinColumn(name = "director_id", referencedColumnName = "employeeID", nullable = false)
    private Doctor director;

    @OneToMany(mappedBy = "department")
    private List<Ward> wards;

    // Getters and Setters
}

