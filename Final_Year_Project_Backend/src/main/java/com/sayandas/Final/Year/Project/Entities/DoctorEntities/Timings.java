package com.sayandas.Final.Year.Project.Entities.DoctorEntities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Timings {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int timingId;
    private String timeRange;
    private int noOfPatients;
    @ManyToOne
    @JoinColumn(name = "schId")
    private Schedule schedule;
}
