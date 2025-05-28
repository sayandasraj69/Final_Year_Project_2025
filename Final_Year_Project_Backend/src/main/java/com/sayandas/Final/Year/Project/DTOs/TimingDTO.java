package com.sayandas.Final.Year.Project.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimingDTO {
    private String timingRange;
    private Integer noOfPatients;
    private String city;
    private String center;
}
