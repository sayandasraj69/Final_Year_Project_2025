package com.sayandas.Final.Year.Project.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleWithTimingsDTO {
    private Integer schId;
    private String weekDay;
    private List<TimingsDTO> timings;
}
