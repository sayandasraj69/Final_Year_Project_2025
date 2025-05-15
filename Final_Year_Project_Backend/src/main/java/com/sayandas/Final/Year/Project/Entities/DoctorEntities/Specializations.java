package com.sayandas.Final.Year.Project.Entities.DoctorEntities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Specializations {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer specId;
    @Column(unique = true)
    private String specName;
    @ManyToMany(mappedBy = "specializations")
    private List<Doctors> doctors;
}
