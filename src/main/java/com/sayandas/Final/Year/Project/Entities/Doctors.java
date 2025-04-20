package com.sayandas.Final.Year.Project.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table
public class Doctors {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotBlank(message = "password is mandatory")
    @Size(message = "minimum 8 characters required")
    private String password;
    @NotBlank
    private String name;
    @NotBlank(message = "email is mandatory")
    @Email(message = "not a valid email")
    private String email;
    @NotBlank(message = "specialization is mandatory")
    private String specialization;


}
