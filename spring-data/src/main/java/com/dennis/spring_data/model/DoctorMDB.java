package com.dennis.spring_data.model;


import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoctorMDB {
    @Id
    private Integer id;

    private String firstName;
    private String lastName;
    private String email;
    private String phone;

}

