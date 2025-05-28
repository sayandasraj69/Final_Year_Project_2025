package com.sayandas.Final.Year.Project.Entities.DoctorEntities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Timings {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer timingId;
    private String timeRange;
    private int noOfPatients;

    @ManyToOne
    @JoinColumn(name = "schId")
    private Schedule schedule;

    @ManyToOne
    @JoinColumn(name = "cityId")
    private City city;
    @ManyToOne
    @JoinColumn(name = "centerId")
    private Centers center;
}
