package com.sayandas.Final.Year.Project.Entities.DoctorEntities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

//@Entity
//@Table(name = "Doctor_Schedule")
@Data
public class Schedule {
    @Id
    private Integer schId;
    private String schDay;
    private String schTime;
    private Integer NoOfPatients;
}
