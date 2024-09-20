package com.dennis.spring_data.model;

import lombok.Data;
import jakarta.persistence.*;

import java.util.List;


@Data
@Entity
@Table(name = "wards")
public class Ward {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long wardID;

    @Column(name = "ward_number", nullable = false)
    private Integer wardNumber;

    @Column(name = "number_of_beds", nullable = false)
    private Integer numberOfBeds;

    @OneToOne
    @JoinColumn(name = "supervisor_id", referencedColumnName = "employeeID", nullable = false)
    private Nurse supervisor;

    @ManyToOne
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    @OneToMany(mappedBy = "ward")
    private List<Hospitalization> hospitalizations;

}
