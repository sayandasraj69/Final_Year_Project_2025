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
public class Qualifications {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer qualId;
    private String qualName;

    @ManyToMany(mappedBy = "qualifications")
    private List<Doctors> doctors;
}
