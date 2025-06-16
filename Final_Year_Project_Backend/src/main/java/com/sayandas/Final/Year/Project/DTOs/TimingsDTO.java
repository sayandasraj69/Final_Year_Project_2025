package com.sayandas.Final.Year.Project.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimingsDTO {
    private Integer timingId;
    private String timeRange;
    private Integer noOfPatients;
    private String city;
    private String center;
}
